package com.mt.bm.bom.service.impl;

import com.mt.bm.bom.service.BomService;
import com.mt.bm.bom.service.MatService;
import com.mt.bm.common.dao.BomMapper;
import com.mt.bm.common.dao.BomNodeMapper;
import com.mt.bm.common.model.Bom;
import com.mt.bm.common.model.BomNode;
import com.mt.bm.common.model.Mat;
import com.mt.cms.common.dao.CompanyMapper;
import com.mt.cms.common.model.Company;
import com.mt.cms.company.service.CompanyService;
import com.mt.common.bo.BeanToMapUtil;
import com.mt.common.core.mybatis.BaseMybatisDao;
import com.mt.common.utils.StringUtils;
import com.mt.pc.common.model.PcRequirePlan;
import com.mt.pc.purchase.service.CreateMatRequirePlanService;
import com.mt.pm.common.dao.PmProdReqMapper;
import com.mt.pm.common.model.PmProdReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BomserviceImpl extends BaseMybatisDao<BomMapper> implements BomService {

    @Autowired
    private MatService matService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private BomMapper bomMapper;
    @Autowired
    private BomNodeMapper nodeMapper;
    @Autowired
    private CreateMatRequirePlanService  createMatRequirePlanService;
    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private PmProdReqMapper pmProdReqMapper;


    @Override
    public int deleteByPrimaryKey(Long bomId) {
        return 0;
    }

    @Override
    public int insert(Bom record) {

        return bomMapper.insert(record);
    }

    @Override
    public int insertSelective(Bom record) {
        return 0;
    }

    @Override
    public Bom selectByPrimaryKey(Long bomId) {
        return bomMapper.selectByPrimaryKey(bomId);
    }

    @Override
    public int updateByPrimaryKeySelective(Bom record) {
        return bomMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Bom record) {
        return 0;
    }

    @Override
    public int selectBomByConditionsCount(Map queryMap) {
        return bomMapper.selectBomByConditionsCount(queryMap);
    }

    /**
     * 获取Bom列表
     * @return
     */
    @Override
    public Map<String, Object> selectBomByConditions(Map<String, Object> map) {
        Map<String, Object> query = (Map<String, Object>) map.get("query");
        map.put("appFlag",query.get("appFlag"));
        map.put("page", ((Integer) map.get("curr_page") - 1) * (Integer) map.get("page_size"));
        Integer totalCount = bomMapper.selectBomByConditionsCount(map);
        List<Map<String, Object>> list = bomMapper.selectBomByConditions(map);
        if(list.size()<=0){
            return StringUtils.pageList(map,"bom_list",0);
        }
        Map<String, Object> page = StringUtils.page(map, list, "bom_list", totalCount);
        return page;
    }


    /**
     * 继承 BaseMybatisDao<BomMapper>
     *     findPage()
     *      xml 中  limit  直接 写 ${page_sql}   不写 #{page}, #{page_size}
     *
     */
//    public Pagination selectBomByConditions(Map<String, Object> map) {
//        Map<String, Object> query = (Map<String, Object>) map.get("query");
//        map.put("appFlag",query.get("appFlag"));
//        Pagination page = findPage("selectBomByConditions", "selectBomByConditionsCount", map, (Integer) map.get("curr_page"), (Integer) map.get("page_size"));
//        return page;
//    }



    @Override
    public BomNode selectBomNode(Long nodeId) {
        return nodeMapper.selectByPrimaryKey(nodeId);
    }

    @Override
    public boolean deleteBomNode(Long nodeId) {
        BomNode bomNode=this.selectBomNode(nodeId);
        Bom bom=this.selectByPrimaryKey(bomNode.getBomId());
        //删除操作--审批通过再做修改的，删除时为逻辑删除;新创建未审批或是审批不通过的，删除时为物理删除
        if(bom.getEdition()>1)
        {

        }
        else {
            if (bomNode.getMatType().equals("标准件"))
            {
                nodeMapper.deleteByPrimaryKey(nodeId);
            }
            else if (bomNode.getMatType().equals("半成品")){
                //判断是否关联电极，有先删除关联电极及下层节点及物料
                BomNode pNode= this.selectFirstEBomNode(bomNode.getMatId());
                if (pNode!=null)
                {
                    getAndDelBomNode(pNode.getNodeId());
                }
                //删除节点及物料
                getAndDelBomNode(bomNode.getNodeId());
            }
            else
            {
                getAndDelBomNode(bomNode.getNodeId());
            }
        }
        return true;
    }

    //递归删除下层物料及节点
    private  void  getAndDelBomNode(Long nodeId) {
        //获取本层节点信息
        BomNode bomNode = this.selectBomNode(nodeId);
        //获取下层节点信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bom_id", bomNode.getBomId());
        map.put("node_id", bomNode.getNodeId());
        List<BomNode> nodeList = this.selectBomNodes(map);
        //先看没无下层节点，有下层节点断续查
        if (nodeList.size() > 0) {
            for (int i = 0; i < nodeList.size(); i++) {
                try {
                    Map<String, Object> map1 = (Map) nodeList.get(i);
                    getAndDelBomNode(Long.parseLong(map1.get("node_id").toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //删除关联的物料及当层节点
        if (!bomNode.getMatType().equals("标准件")) {
            matService.deleteByPrimaryKey(bomNode.getMatId());
        }
        nodeMapper.deleteByPrimaryKey(bomNode.getNodeId());
    }




    public boolean  createOrUpdateBomNode(Long pNodeId,BomNode bomNode,Mat mat) {
        if(!"标准件".equals(bomNode.getMatType()))
        {
            if ("1".equals(mat.getShape())) {
                mat.setSpec(mat.getLength() + "X" + mat.getWidth() + "X" + mat.getHeight());
            } else if ("2".equals(mat.getShape())) {
                mat.setSpec(mat.getLength() + "X" + mat.getHeight());
            }
        }
        SimpleDateFormat sdf  =   new  SimpleDateFormat( "yyyyMMddHHmmss" );
        mat.setMinStockQty(new BigDecimal(0));
        mat.setBatchQty(new BigDecimal(0));
        mat.setHandlerId("20");
        mat.setHandledTime(new Date());
        if(null==(mat.getPlanPrice())||"".equals(mat.getPlanPrice())){
            mat.setPlanPrice(new BigDecimal(0));
        }
        if(bomNode.getNodeId()== null)
        {
            //获取父级节点信息，计算当前节点信息
            BomNode pNode=this.selectBomNode(pNodeId);
            bomNode.setBomId(pNode.getBomId());
            bomNode.setParentPath(pNode.getPath());
            bomNode.setPath(pNode.getPath()+this.getNewBomNodePath(pNode.getNodeId(),"son"));
            bomNode.setNodePos((int)0);
            bomNode.setIsUnused("0");
            bomNode.setDepth((int)0);
            bomNode.setSortOrder((int)0);
            if(bomNode.getMatType().equals("半成品")) {
                mat.setMatNo(pNode.getMatNo() +"-" +  sdf.format(new Date()));
                mat.setVersion((int) 1);
                mat.setUnitNo("PCS");
                bomNode.setUnitNo("PCS");
                bomNode.setMatNo(mat.getMatNo());

                if(mat.getMatName().equals("外购模胚"))
                {
                    mat.setMatTypeName("模胚");
                    bomNode.setIsPurchase("0");
                }
                matService.insert(mat);
                bomNode.setMatId(mat.getMatId());
                this.insertBomNode(bomNode);

                if (bomNode.getIsPurchase().toString().equals("1")) {
                    mat.setMatId(null);
                    mat.setMatTypeId((long) 4);
                    mat.setMatTypeName("毛坯");
                    mat.setProcureStyle("1");
                    mat.setMatNo(mat.getMatNo() + "-" + sdf.format(new Date()));
                    matService.insert(mat);
                    bomNode.setMatId(mat.getMatId());
                    bomNode.setParentPath(bomNode.getPath());
                    bomNode.setPath(bomNode.getPath() + this.getNewBomNodePath(bomNode.getNodeId(),"son"));
                    bomNode.setMatNo(mat.getMatNo());
                    bomNode.setMatType("毛坯");
                    bomNode.setIsPatentBuild("1");
                    bomNode.setNodeId(null);
                    this.insertBomNode(bomNode);
                }
            }
            else if(bomNode.getMatType().equals("标准件")) {
                mat.setVersion((int) 1);
                mat.setUnitNo("PCS");
                bomNode.setUnitNo("PCS");
                bomNode.setMatNo(mat.getMatNo());
                bomNode.setMatId(mat.getMatId());
                this.insertBomNode(bomNode);
            }
            else if(bomNode.getMatType().equals("电极")) {
                String matNoE=pNode.getMatNo();
                String matNoSerialNo=sdf.format(new Date());
                String matNoSerialNo2=sdf.format(new Date());
                mat.setMatNo(matNoE);
                //电极首节点，只为记录关联关系
                //如果已经创建电极首节点，把首节点信息获取
                BomNode newEnode=this.selectFirstEBomNode(pNode.getMatId());
                if (newEnode!=null)
                {
                    bomNode.setUnitNo("PCS");
                    bomNode.setNodeId(newEnode.getNodeId());
                    bomNode.setPath(newEnode.getPath());
                    bomNode.setParentPath(newEnode.getParentPath());
                }
                else {
                    if(pNode.getMatType().equals("半成品")) {
                        bomNode.setParentPath(pNode.getParentPath());
                        bomNode.setPath(pNode.getParentPath() + this.getNewBomNodePath(pNode.getNodeId(), "brother"));
                    }
                    bomNode.setMatId(pNode.getMatId());
                    bomNode.setMatNo(pNode.getMatNo());
                    bomNode.setUnitNo("");
                    this.insertBomNode(bomNode);
                }
                String nodePathE=bomNode.getPath();
                Long nodeIdE=bomNode.getNodeId();
                bomNode.setUnitNo("PCS");
                if (null!=bomNode.getProcessesRCount()&&bomNode.getProcessesRCount()>0)
                {
                    //添加电极
                    mat.setMatId(null);
                    mat.setVersion((int) 1);
                    mat.setUnitNo("PCS");
                    mat.setMatTypeId((long) 3);
                    mat.setMatTypeName("电极");
                    mat.setMatNo(matNoE + "-E" + matNoSerialNo + "R");
                    matService.insert(mat);
                    bomNode.setMatId(mat.getMatId());
                    bomNode.setParentPath(nodePathE);
                    bomNode.setPath(nodePathE + this.getNewBomNodePath(nodeIdE,"son"));
                    bomNode.setMatNo(mat.getMatNo());
                    bomNode.setMatType(mat.getMatTypeName());
                    bomNode.setTotalQty(new BigDecimal(bomNode.getProcessesRCount()));
                    bomNode.setNodeId(null);
                    this.insertBomNode(bomNode);
                    //添加电极的下层物料电极毛坯

                    mat.setMatId(null);
                    mat.setMatTypeId((long) 4);
                    mat.setMatTypeName("毛坯");
                    mat.setMatNo(mat.getMatNo() + "-" + matNoSerialNo2);
                    matService.insert(mat);
                    bomNode.setMatId(mat.getMatId());
                    bomNode.setParentPath(bomNode.getPath());
                    bomNode.setPath(bomNode.getPath() + this.getNewBomNodePath(bomNode.getNodeId(),"son"));
                    bomNode.setMatNo(mat.getMatNo());
                    bomNode.setMatType("毛坯");
                    bomNode.setIsPatentBuild("1");
                    bomNode.setNodeId(null);
                    this.insertBomNode(bomNode);
                }
                if (null!=bomNode.getProcessesZCount()&&bomNode.getProcessesZCount()>0)
                {
                    //添加电极
                    mat.setMatId(null);
                    mat.setVersion((int) 1);
                    mat.setUnitNo("PCS");
                    mat.setMatTypeId((long) 3);
                    mat.setMatTypeName("电极");
                    mat.setMatNo(matNoE + "-E" + matNoSerialNo+"Z");
                    matService.insert(mat);
                    bomNode.setMatId(mat.getMatId());
                    bomNode.setParentPath(nodePathE);
                    bomNode.setPath(nodePathE + this.getNewBomNodePath(nodeIdE,"son"));
                    bomNode.setMatNo(mat.getMatNo());
                    bomNode.setMatType(mat.getMatTypeName());
                    bomNode.setTotalQty(new BigDecimal(bomNode.getProcessesZCount()));
                    bomNode.setNodeId(null);
                    this.insertBomNode(bomNode);
                    //添加电极的下层物料电极毛坯

                    mat.setMatId(null);
                    mat.setMatTypeId((long) 4);
                    mat.setMatTypeName("毛坯");
                    mat.setMatNo(mat.getMatNo() + "-" + matNoSerialNo2);
                    matService.insert(mat);
                    bomNode.setMatId(mat.getMatId());
                    bomNode.setParentPath(bomNode.getPath());
                    bomNode.setPath(bomNode.getPath() + this.getNewBomNodePath(bomNode.getNodeId(),"son"));
                    bomNode.setMatNo(mat.getMatNo());
                    bomNode.setMatType("毛坯");
                    bomNode.setIsPatentBuild("1");
                    bomNode.setNodeId(null);
                    this.insertBomNode(bomNode);
                }
                if (null!=bomNode.getProcessesFCount()&&bomNode.getProcessesFCount()>0)
                {
                    //添加电极
                    mat.setMatId(null);
                    mat.setVersion((int) 1);
                    mat.setUnitNo("PCS");
                    mat.setMatTypeId((long) 3);
                    mat.setMatTypeName("电极");
                    mat.setMatNo(matNoE + "-E" + matNoSerialNo+"F");
                    matService.insert(mat);
                    bomNode.setMatId(mat.getMatId());
                    bomNode.setParentPath(nodePathE);
                    bomNode.setPath(nodePathE + this.getNewBomNodePath(nodeIdE,"son"));
                    bomNode.setMatNo(mat.getMatNo());
                    bomNode.setMatType(mat.getMatTypeName());
                    bomNode.setTotalQty(new BigDecimal(bomNode.getProcessesFCount()));
                    bomNode.setNodeId(null);
                    this.insertBomNode(bomNode);
                    //添加电极的下层物料电极毛坯

                    mat.setMatId(null);
                    mat.setMatTypeId((long) 4);
                    mat.setMatTypeName("毛坯");
                    mat.setMatNo(mat.getMatNo() + "-" + matNoSerialNo2);
                    matService.insert(mat);
                    bomNode.setMatId(mat.getMatId());
                    bomNode.setParentPath(bomNode.getPath());
                    bomNode.setPath(bomNode.getPath() + this.getNewBomNodePath(bomNode.getNodeId(),"son"));
                    bomNode.setMatNo(mat.getMatNo());
                    bomNode.setMatType("毛坯");
                    bomNode.setIsPatentBuild("1");
                    bomNode.setNodeId(null);
                    this.insertBomNode(bomNode);
                }
            }
            else if(bomNode.getMatType().equals("毛坯")) {
                mat.setMatId(null);
                mat.setVersion((int) 1);
                mat.setUnitNo("PCS");
                mat.setMatTypeId((long) 4);
                mat.setMatTypeName("毛坯");
                mat.setProcureStyle("1");
                mat.setMatNo(pNode.getMatNo() + "-" + sdf.format(new Date()));
                matService.insert(mat);
                bomNode.setMatId(mat.getMatId());
                bomNode.setMatNo(mat.getMatNo());
                bomNode.setUnitNo("PCS");
                bomNode.setMatType("毛坯");
                bomNode.setIsPatentBuild("1");
                bomNode.setNodeId(null);
                this.insertBomNode(bomNode);
            }
        }
        else {
            if(bomNode.getMatType().equals("半成品")) {
                if(mat.getMatName().equals("外购模胚"))
                {
                    mat.setMatTypeName("模胚");
                    bomNode.setIsPurchase("0");
                }
                //修改bom节点以及关联的物料信息
                BomNode oldBomNode = this.selectBomNode(bomNode.getNodeId());
                int i = matService.updateByPrimaryKeySelective(mat);
                int j = this.updateByPrimaryKeySelective(bomNode);
                //检查是否需要生成下层毛坏，检查是否已经生成下层毛坏
                if (oldBomNode.getIsPurchase().toString().equals("0") && bomNode.getIsPurchase().toString().equals("1")) {
                    mat.setMatId(null);
                    mat.setMatTypeId((long) 4);
                    mat.setMatTypeName("毛坯");
                    mat.setVersion((int) 1);
                    mat.setUnitNo("PCS");
                    mat.setMatNo(mat.getMatNo() + "mp" + sdf.format(new Date()));
                    //mat.setMatName(mat.getMatName()+"材料");
                    matService.insert(mat);
                    bomNode.setMatId(mat.getMatId());
                    bomNode.setParentPath(oldBomNode.getPath());
                    bomNode.setPath(oldBomNode.getPath() + this.getNewBomNodePath(bomNode.getNodeId(),"son"));
                    bomNode.setMatNo(mat.getMatNo());
                    bomNode.setMatType("毛坯");
                    bomNode.setUnitNo("PCS");
                    bomNode.setIsUnused("0");
                    bomNode.setNodePos((int) 0);
                    bomNode.setDepth((Integer) 0);
                    bomNode.setSortOrder((Integer) 0);
                    bomNode.setIsPatentBuild("1");
                    bomNode.setNodeId(null);
                    this.insertBomNode(bomNode);
                } else if (oldBomNode.getIsPurchase().toString().equals("1") && bomNode.getIsPurchase().toString().equals("1")) {
                    //对已生成的毛坯做修改操作
                    //修改半成品的下层物料毛坯(默认)
                    BomNode updateBomNode = this.getSonDefaultBomNode(bomNode.getNodeId());
                    if(updateBomNode!=null) {
                        updateBomNode.setRemark(bomNode.getRemark());
                        updateBomNode.setTotalQty(bomNode.getTotalQty());
                        Mat updateMat=matService.selectByPrimaryKey(bomNode.getMatId());
                        updateMat.setPlanPrice(mat.getPlanPrice());
                        updateMat.setShape(mat.getShape());
                        updateMat.setRawnatureId(mat.getRawnatureId());
                        updateMat.setRawnatureName(mat.getRawnatureName());
                        updateMat.setWeight(mat.getWeight());
                        updateMat.setLength(mat.getLength());
                        updateMat.setWidth(mat.getWidth());
                        updateMat.setHeight(mat.getHeight());
                        matService.updateByPrimaryKeySelective(updateMat);
                        this.updateByPrimaryKeySelective(updateBomNode);
                    }
                }
            }
            else if(bomNode.getMatType().equals("标准件")) {
                this.updateByPrimaryKeySelective(bomNode);
            }
            else if(bomNode.getMatType().equals("电极")) {
                //无物料id 视为需要在此节点添加下层物料，需新建物料及节点(毛坯)
                if(mat.getMatId()==null) {
                    BomNode oldBomNode = this.selectBomNode(bomNode.getNodeId());
                    Long nodeIdE = oldBomNode.getNodeId();
                    String nodePathE = oldBomNode.getPath();
                    String matNoSerialNo2 = sdf.format(new Date());

                    mat.setVersion((int) 1);
                    mat.setUnitNo("PCS");
                    mat.setMatTypeId((long) 4);
                    mat.setMatTypeName("毛坯");
                    mat.setMatNo(mat.getMatNo() + "-" + matNoSerialNo2);
                    matService.insert(mat);
                    bomNode.setMatId(mat.getMatId());
                    bomNode.setParentPath(nodePathE);
                    bomNode.setPath(nodePathE + this.getNewBomNodePath(nodeIdE,"son"));
                    bomNode.setMatNo(mat.getMatNo());
                    bomNode.setMatType("毛坯");
                    bomNode.setIsPatentBuild("1");
                    bomNode.setNodeId(null);
                    this.insertBomNode(bomNode);

                }
                else {
                    //对已生成毛坯电极节点做修改操作
                    //修改电极
                    matService.updateByPrimaryKeySelective(mat);
                    this.updateByPrimaryKeySelective(bomNode);
                    //修改电极的下层物料电极毛坯
                    BomNode updateBomNode = this.getSonDefaultBomNode(bomNode.getNodeId());
                    if(updateBomNode!=null) {
                        updateBomNode.setRemark(bomNode.getRemark());
                        updateBomNode.setTotalQty(bomNode.getTotalQty());
                        Mat updateMat=matService.selectByPrimaryKey(bomNode.getMatId());
                        updateMat.setPlanPrice(mat.getPlanPrice());
                        updateMat.setShape(mat.getShape());
                        updateMat.setRawnatureId(mat.getRawnatureId());
                        updateMat.setRawnatureName(mat.getRawnatureName());
                        updateMat.setWeight(mat.getWeight());
                        updateMat.setLength(mat.getLength());
                        updateMat.setWidth(mat.getWidth());
                        updateMat.setHeight(mat.getHeight());
                        updateMat.setProcureStyle(mat.getProcureStyle());
                        matService.updateByPrimaryKeySelective(updateMat);
                        this.updateByPrimaryKeySelective(updateBomNode);
                    }
                }
            }
            else if(bomNode.getMatType().equals("毛坯")) {
                //修改毛坯
                matService.updateByPrimaryKeySelective(mat);
                this.updateByPrimaryKeySelective(bomNode);
            }
        }
       return true;
    }

    @Override
    public int deleteByPNodeId(Map map) {
        return nodeMapper.deleteByPNodeId(map);
    }
    @Override
    public int  deleteMatByPNodeId(Map map) {
        return nodeMapper.deleteMatByPNodeId(map);
    }

    @Override
    public int insertBomNode(BomNode bomNode){
        return  nodeMapper.insert(bomNode);
    }

    @Override
    public int updateBomNode(BomNode bomNode) {
        return nodeMapper.updateByPrimaryKey(bomNode);
    }

    @Override
    public int updateByPrimaryKeySelective(BomNode bomNode) {
        return nodeMapper.updateByPrimaryKeySelective(bomNode);
    }


    //创建节点路径
    //son创建兄弟节点路径
    //brother创建兄弟节点路径
    @Override
    public String getNewBomNodePath(Long nodeId,String type) {
        int lnum=0;
        try {
            String lastNodePath = "son";
            if (type.equals("son")) {
                lastNodePath = nodeMapper.getNewBomNodePath(nodeId);
            }
            else if (type.equals("brother")) {
                lastNodePath = nodeMapper.getNewBomNodeBrotherPath(nodeId);
            }
            if (StringUtils.isNotEmpty(lastNodePath)) {
                String lastNum = lastNodePath.substring(lastNodePath.lastIndexOf("-") + 1);
                lnum = Integer.parseInt(lastNum) + 1;
            }
        }
        catch (Exception ex)
        {
            logger.info(ex.getMessage());
        }
        return "-"+lnum;
    }


    @Override
    public BomNode getSonDefaultBomNode(Long bomId) {
        return nodeMapper.getSonDefaultBomNode(bomId);
    }

    @Override
    public List<BomNode> selectBomNodes(Map param){
        return  nodeMapper.selectBomNodes(param);
    }

    @Override
    public boolean createBom(String is_crt_bom, String mold_no,String uid,String companyId,Integer bomQty,Date first_try_date) {
        try {
            //判断此Bom是否创建
            Map<String, Object> map = new HashMap<>();
            map.put("company_id",companyId);
            map.put("moldNo",mold_no);
            Long bomId=this.selectBomIdByMoldNo(map);
            if (bomId!=null && bomId>0)
            {
                logger.info("Bom已存在！");
                return false;
            }
            //
            String isUnused="0";
            if(is_crt_bom=="0") {
                isUnused="1";
            }
            Company company=companyService.selectByCompanyId(Integer.parseInt(companyId));
            String compyName=company.getCompanyName();
            Bom bom=new Bom();
            bom.setMoldNo(mold_no);
            bom.setIsShow(is_crt_bom);
            bom.setCompanyId(companyId);
            bom.setCompanyName(compyName);
            bom.setBomType( "1");
            bom.setEdition((int)1);
            bom.setAppFlag("step10");
            bom.setMaxNodepos(null);
            bom.setHandlerId(uid);
            bom.setHandledTime(new Date());
            bom.setFirstTryDate(first_try_date);
            this.insert(bom);
            Mat mat=new Mat();
            mat.setMatTypeId((long)5);
            mat.setMatTypeName("成品");
            mat.setMatName(mold_no);
            mat.setMatNo(mold_no);
            mat.setVersion((int)0);
            mat.setCompanyId(companyId);
            mat.setCompanyName(compyName);
            mat.setMinStockQty(new BigDecimal(0));
            mat.setBatchQty(new BigDecimal(0));
            mat.setUnitNo("PCS");
            mat.setPlanPrice(new BigDecimal(0));
            mat.setIsUnused(isUnused);
            mat.setHandlerId(uid);
            mat.setHandledTime(new Date());
            long matId=matService.insert(mat);
            BomNode bomNode=new BomNode();
            bomNode.setMatId(mat.getMatId());
            bomNode.setBomId(bom.getBomId());
            bomNode.setIsUnused(isUnused);
            bomNode.setPath("0");
            bomNode.setDepth((int)0);
            bomNode.setMatType("成品");
            bomNode.setMatNo(mold_no);
            bomNode.setUnitNo("PCS");
            bomNode.setIsPurchase("0");
            bomNode.setSortOrder((int)0);
            bomNode.setNodePos((int)0);
            bomNode.setTotalQty(new BigDecimal(bomQty));
            int bomNodeId=this.insertBomNode(bomNode);
            if (bomNodeId>0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            return  false;
        }
    }

    @Override
    public List<BomNode> selectEBomNodes(Map map) {
        return nodeMapper.selectEBomNodes(map);
    }

    @Override
    public BomNode selectFirstEBomNode(Long matId) {
        return nodeMapper.selectFirstEBomNode(matId);
    }

    @Override
    public boolean appBomRequire(Long bomId)  {
        Bom appBom=this.bomMapper.selectByPrimaryKey(bomId);
        //产生需求
        List<Map<String, Object>> matList= this.selectMatByBomId(bomId);
        int pos=appBom.getMaxNodepos()==null?0:appBom.getMaxNodepos();
        BomNode node;
        int iPos=0;
        for (int i=0;i<matList.size();i++) {
            Map map=(Map)matList.get(i);
            node=new BomNode();
            node.setNodeId(Long.parseLong(map.get("node_id").toString()));
            if(null==map.get("nodePos")||"".equals(map.get("nodePos").toString())) {
                iPos++;
                node.setNodePos(pos + iPos);
                //产生版本（暂时）
                nodeMapper.updateByPrimaryKeySelective(node);
                map.put("nodePos",node.getNodePos().toString());
            }
            if (map.get("matTypeName").toString().equals("毛坯")) {
                if(map.get("procureStyle")!=null&& map.get("procureStyle").toString().equals("2")) {
                    //下料需求
                }
                else {
                    //物料需求计划,暂未实现库存供应
                    PcRequirePlan pcRequirePlan = null;
                    try {
                        map.put("matDesc",map.get("matName").toString());
                        pcRequirePlan = (PcRequirePlan) BeanToMapUtil.convertMap(PcRequirePlan.class,(Map)map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    createMatRequirePlanService.createMatRequirePlanfromBom(pcRequirePlan);
                }
            }
            else if (map.get("matTypeName").toString().equals("模胚")||map.get("matTypeName").toString().equals("标准件")) {
                //物料需求计划,暂未实现库存供应
                PcRequirePlan pcRequirePlan = null;
                try {
                    map.put("matDesc",map.get("matName").toString());
                    pcRequirePlan = (PcRequirePlan) BeanToMapUtil.convertMap(PcRequirePlan.class,(Map)map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                createMatRequirePlanService.createMatRequirePlanfromBom(pcRequirePlan);
            }else if (map.get("matTypeName").toString().equals("半成品")||map.get("matTypeName").toString().equals("成品")) {
                //生产需求
                Map<String,Object>  mapReq =new HashMap<>();
                mapReq.put("company_id",map.get("companyId").toString());
                mapReq.put("mold_no",map.get("moldNo").toString());
                mapReq.put("bom_id",bomId);
                mapReq.put("mat_id",map.get("matId").toString());
                mapReq.put("node_pos",map.get("nodePos").toString());
                PmProdReq pmProdReq = pmProdReqMapper.selectReqBycondition(mapReq);
                if(null==pmProdReq){
                    try {
                        map.put("undealQty",(int)Double.parseDouble(map.get("reqQty").toString()));
                        pmProdReq = (PmProdReq) BeanToMapUtil.convertMap(PmProdReq.class,map);
                        pmProdReq.setBomId(bomId);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pmProdReq.setCreatedAt(new Date());
                    pmProdReq.setUpdatedAt(new Date());
                    pmProdReq.setReqType("1");
                    pmProdReq.setBomType("1");
                    pmProdReq.setStatus("1");
                    pmProdReqMapper.insert(pmProdReq);
                }
                else {
                    pmProdReq.setReqQty(pmProdReq.getReqQty()+(int)Double.parseDouble(map.get("reqQty").toString()));
                    pmProdReq.setUndealQty(pmProdReq.getUndealQty()+(int)Double.parseDouble(map.get("reqQty").toString()));
                    pmProdReq.setUpdatedAt(new Date());
                    pmProdReqMapper.updateByPrimaryKeySelective(pmProdReq);
                }

            }
        }

        //Bom版本生成
        appBom.setMaxNodepos(pos + iPos);
        this.bomMapper.updateByPrimaryKeySelective(appBom);
        return  true;
    }


    @Override
    public boolean appBom(Bom bom)  {
        //  `company_id` int(12) DEFAULT NULL COMMENT '公司id',
        //  `company_name` varchar(256) DEFAULT NULL COMMENT '公司名称',
        //  `bom_id` bigint(20) DEFAULT NULL COMMENT 'BomId',
        //  `app_flag` varchar(32) DEFAULT NULL COMMENT '创建类型： 1 Bom; 2 外协; 3 请购单;4 仓库报警; 5 库存下料；6工序转外协',
        //  `app_id` varchar(32) DEFAULT NULL COMMENT '审批人',
        Bom appBom=new Bom();
        appBom.setBomId(bom.getBomId());
        appBom.setAppFlag(bom.getAppFlag());
        appBom.setAppId(bom.getAppId());
        appBom.setAppRemark(bom.getAppRemark());
        appBom.setAppTime(new Date());
        int result = this.updateByPrimaryKeySelective(appBom);
        if (result>0)
        {
            if(bom.getAppFlag().equals("step0"))//审批通过
            {
                //产生需求
                List<Map<String, Object>> matList= this.selectMatByBomId(bom.getBomId());
                for (int i=0;i<matList.size();i++) {
                    if (matList.get(i).get("matTypeName").toString().equals("毛坯")) {
                        if(matList.get(i).get("procureStyle")!=null&& matList.get(i).get("procureStyle").toString().equals("2")) {
                            //下料需求
                        }
                        else {
                            //物料需求计划,暂未实现库存供应
                            PcRequirePlan pcRequirePlan = null;
                            try {
                                pcRequirePlan = (PcRequirePlan) BeanToMapUtil.convertMap(PcRequirePlan.class,(Map)matList.get(i));
                                pcRequirePlan.setCompanyId( Integer.parseInt(bom.getCompanyId()));
                                pcRequirePlan.setCompanyName(bom.getCompanyName());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            createMatRequirePlanService.createMatRequirePlanfromBom(pcRequirePlan);
                        }
                    }
                    else if (matList.get(i).get("matTypeName").toString().equals("模胚")||matList.get(i).get("matTypeName").toString().equals("标准件")) {
                        //物料需求计划,暂未实现库存供应
                        PcRequirePlan pcRequirePlan = null;
                        try {
                            pcRequirePlan = (PcRequirePlan) BeanToMapUtil.convertMap(PcRequirePlan.class,(Map)matList.get(i));
                            pcRequirePlan.setCompanyId( Integer.parseInt(bom.getCompanyId()));
                            pcRequirePlan.setCompanyName(bom.getCompanyName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        createMatRequirePlanService.createMatRequirePlanfromBom(pcRequirePlan);
                    }else if (matList.get(i).get("matTypeName").toString().equals("半成品")) {
                        //生产需求
                    }
                }
                //Bom版本生成
            }
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Long selectBomIdByMoldNo(Map queryMap) {
        return bomMapper.selectBomIdByMoldNo(queryMap);
    }

    @Override
    public List<Map<String, Object>> selectMatByBomId(Long bomId) {
        return bomMapper.selectMatByBomId(bomId);
    }


}
