/**
 * Created by zxf on 2017/9/5.
 */

import Index from "@/views/hello";
import HomeLayout from "@/views/index/layout";
import Home from "@/views/index/home";
import Notice from "@/views/index/notice";
import Company from "@/views/company";
import MyCompany from "@/views/company/myCompany";

import Login from "@/views/user/login/login";
//客户定单相关
import CustomerOrder from "@/views/customerOrder";
import ComputeList from "@/views/customerOrder/quotes/compute/list";
import QuotesList from "@/views/customerOrder/quotes/quotes/list";
import orderList from "@/views/customerOrder/orders/list";
import manageList from "@/views/customerOrder/management/list";
//采购管理
import Purchase from "@/views/purchase";
//物料管理
import Mats from "@/views/mats";
//仓库管理
import Stroage from '@/views/storage'
//审批相关
import Approve from '@/views/approve'
//供应商&客户相关
import CRM from '@/views/crm'
//生产管理
import  Production from '@/views/production'
// 统计页面
import  statistics from '@/views/statistics'


/*
 * meta
 * home: a flag for home,
 * withoutLogin: this route can be reach without login
 * */

const routes = [
  {
    path: '/hello',
    name: 'hello',
    component: Index,
    meta: {
      withoutLogin: true
    }
  },
  {
    path: '/',
    name: 'index',
    component: HomeLayout,
    redirect: '/home',
    children: [
      {path: 'home', name: 'home', component: Home, meta: {home: true}},
      {path: 'notice', name: 'notice', component: Notice, meta: {home: true}},
      {
        path: 'relationship', name: 'relationship', component: function (reslove) {
          require(['@/views/index/relationship'], reslove)
        }, meta: {home: true}
      },
      {
        path: 'my', name: 'my', component: function (resolve) {
          require(['@/views/index/my'], resolve)
        }, meta: {home: true}
      }
    ]
  },

  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: {
      withoutLogin: true
    }
  },
  {
    path: '/bindWX',
    name: 'bindWX',
    component: function (resolve) {
      require(['@/views/user/login/bindWX'], resolve)
    },
    meta: {
      withoutLogin: true
    }
  },
  {
    path: '/apps', name: 'apps', component: function (resolve) {
      require(['@/views/index/apps'], resolve)
    }, meta: {home: true}
  },
  {
    path: '/chooseCompany',
    name: 'chooseCompany',
    component: function (resolve) {
      require(['@/views/company/chooseCompany'], resolve)
    },
  },
  {
    path: '/regist',
    name: 'regist',
    component: function (resolve) {
      require(['@/views/user/login/regist'], resolve)
    },
    meta: {
      withoutLogin: true
    }
  },
  {
    path: '/zoneCode',
    name: 'zoneCode',
    component: function (resolve) {
      require(['@/views/user/userinfo/zoneCode'], resolve)
    },
    meta: {
      withoutLogin: true
    }
  },
  {
    path: '/settings',
    name: 'settings',
    component: function (resolve) {
      require(['@/views/user/settings/index'], resolve)
    }
  },
  //试用
  {
    path: '/company/trial',
    name: 'trialVersion',
    component: function (resolve) {
      require(['@/views/company/trial'], resolve)
    }
  },
  {
    path: '/findPWD/:type',
    name: 'findPWD',
    component: function (resolve) {
      require(['@/views/user/login/findPWD'], resolve)
    },
    meta: {
      withoutLogin: true
    }
  },
  {
    path: '/changePWd',
    name: 'changePWd',
    component: function (resolve) {
      require(['@/views/user/login/changePWD'], resolve)
    },
    meta: {
      withoutLogin: true
    }
  },
  {
    path: '/userinfo',
    name: 'userinfo',
    component: function (resolve) {
      require(['@/views/user/userinfo/index'], resolve)
    }
  },
  //修改用户信息
  {
    path: '/modifyUserinfo',
    name: 'modifyUserinfo',
    component: function (resolve) {
      require(['@/views/user/userinfo/modifyUserinfo'], resolve)
    }
  },
  {
    path: '/search',
    name: 'search',
    component: function (resolve) {
      require(['@/views/search/index'], resolve)
    }
  },

  // 供应商&客户相关
  {
    path : '/crm',
    name : 'crm',
    component : CRM,
    children:[
      {path:'cstList',name:'cstList', component:resolve=>{require(['@/views/crm/custom/customList'],resolve)}},
      {path:'customInfo',name:'customInfo',component:resolve=>{require(['@/views/crm/custom/customInfo'],resolve)}},
      {path:'newCustom',name:'newCustom',component:resolve=>{require(['@/views/crm/custom/newCustom'],resolve)}},

      {path:'supplyList',name:'supplyList',component:resolve=>{require(['@/views/crm/supply/supplyList'],resolve)}},
      {path:'supplyInfo',name:'supplyInfo',component:resolve=>{require(['@/views/crm/supply/supplyInfo'],resolve)}},
    ]
  },

  {
    path : '/company',
    name : 'company',
    component : Company,
    children: [
      //公司相关
      {
        path: 'index',
        name: 'myCompany',
        component: MyCompany
      },

      //企业详细信息/用于对自己企业信息查看、修改等操作
      {
        path: 'companyDetail/:company_id',
        name: 'companyDetail',
        component: resolve => {require(['@/views/company/companyDetail'], resolve)}
      },
      //企业基本信息，用于查看
      {
        path: 'companyInfo',
        name: 'companyInfo',
        component: resolve => {require(['@/views/company/companyInfo'], resolve)}
      },
      {
        path: 'deptDetail',
        name: 'deptDetail',
        component: function (resolve) {
          require(['@/views/company/deptDetail'], resolve)
        }
      },
      {//子部门
        path: 'subDeptDetail',
        name: 'subDeptDetail',
        component: function (resolve) {
          require(['@/views/company/subDeptDetail'], resolve)
        }
      },
      {   //生产部详子部门
        path: 'productionDeptSub', name: 'productionDeptSub', component: function (resolve) {
          require(['@/views/production/equipment'], resolve)
        }
      },
      {   //设备列表
        path: 'productionDeptSub/equipmentList', name: 'equipmentList', component: function (resolve) {
          require(['@/views/production/equipment/equipmentList'], resolve)
        }
      },
      {   //人员列表
        path: 'productionDeptSub/personnelList', name: 'personnelList', component: function (resolve) {
          require(['@/views/production/equipment/personnelList'], resolve)
        }
      },
      {   //添加设备点检分配
        path: 'productionDeptSub/distributionList', name: 'distributionList', component: function (resolve) {
          require(['@/views/production/equipment/distributionList'], resolve)
        }
      },
      {   //设备点检分配详情
        path: 'productionDeptSub/distributionInfo', name: 'distributionInfo', component: function (resolve) {
          require(['@/views/production/equipment/distributionInfo'], resolve)
        }
      },
      {   //设备点检分配
        path: 'productionDeptSub/checklist', name: 'checklist', component: function (resolve) {
          require(['@/views/production/equipment/checklist'], resolve)
        }
      },
      {   //设备详情
        path: 'productionDeptSub/equipmentDetail', name: 'equipmentDetail', component: function (resolve) {
          require(['@/views/production/equipment/equipmentDetail'], resolve)
        }
      },
      {   //检修单
        path: 'productionDeptSub/overhaulForm', name: 'overhaulForm', component: function (resolve) {
          require(['@/views/production/equipment/overhaulForm'], resolve)
        }
      },
      {
        path: 'memberDetail/:member_id',
        name: 'memberDetail',
        component: function (resolve) {
          require(['@/views/company/memberDetail'], resolve)
        }
      },
      {
        path: 'memberInfo/:member_id',
        name: 'memberInfo',
        component: function (resolve) {
          require(['@/views/company/memberInfo'], resolve)
        }
      },
      //批量选择页面batchSelect.vue
      {
        path: 'batchSelect',
        name: 'batchSelect',
        component: function (resolve) {
          require(['@/views/company/batchSelect'], resolve)
        }
      },
      //批量移动成员 选择部门
      {
        path: 'chooseDep/:dep_id/:type',
        name: 'chooseDep',
        component: function (resolve) {
          require(['@/views/company/chooseDep'], resolve)
        }
      },
      //公司内部邀请搜索页面
      {
        path: 'memberSearch/:type',
        name: 'memberSearch',
        component: function (resolve) {
          require(['@/views/company/memberSearch'], resolve)
        }
      },
      //部门详情
      {
        path: 'deptSetting/:dep_id',
        name: 'deptSetting',
        component: function (resolve) {
          require(['@/views/company/deptSetting'], resolve)
        }
      },
    ]
  },

  //审批相关页面
  {
    path: '/approve',
    name: 'approve',
    redirect:'noredirect',
    component:Approve,
    children:[
      {path: '', name: 'approveMain', component: function (resolve) {require(['@/views/approve/main'], resolve)}},
      {path: 'quotes', name: 'approveQuotes', component: function (resolve) {require(['@/views/approve/customerOrder/quotes'], resolve)}},
      {path: 'computes', name: 'approveComputes', component: function (resolve) {require(['@/views/approve/customerOrder/compute'], resolve)}},
      {path: 'orders', name: 'approveOrders', component: function (resolve) {require(['@/views/approve/customerOrder/orders'], resolve)}},
      {path: 'boms', name: 'approveBoms', component: function (resolve) {require(['@/views/approve/Bom/boms'], resolve)}},
      {path: 'purchases', name: 'approvePurchases', component: function (resolve) {require(['@/views/approve/purchase/purchase'], resolve)}},
      {path: 'requisitions', name: 'approveRequisitions', component: function (resolve) {require(['@/views/approve/purchase/requisition'], resolve)}},
      {path: 'approveMaterialQC', name: 'approveMaterialQC', component: function (resolve) {require(['@/views/approve/qualityCheck/MaterialQC'], resolve)}},
      {path: 'approveProcedureQC', name: 'approveProcedureQC', component: function (resolve) {require(['@/views/approve/qualityCheck/ProcedureQC'], resolve)}},


      //填写审批意见
      {path: 'approve/:bill_id/:bill_type/:approve_step', name: 'approveSuggestion', component: function (resolve) {require(['@/views/approve/approve'], resolve)}},
      //查看审批意见
      {path: 'approveSuggests/:bill_id/:bill_type', name: 'approveSuggests', component: function (resolve) {require(['@/views/approve/approveSuggests'], resolve)}},

    ]

  },


  //客户定单相关

  { //核算单相关表
    path: '/customerOrder',
    name: 'customerOrder',
    component: CustomerOrder,
    redirect: 'noredirect',
    children: [
      { //核算单列表
        path: 'compute',
        name: 'computeList',
        component: ComputeList,
        meta:{keepAlive:true}
      },
      { //新建核算单
        path: 'compute/new',
        name: 'computeDraft',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/compute/newDraft'], resolve)
        },
        meta:{keepAlive:true}
      },
      { //核算单预览
        path: 'computeDetailPreview',
        name: 'computeDetailPreview',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/compute/preview/detail'], resolve)
        },
        meta:{keepAlive:true}
      },
      { //核算单材料预览
        path: 'computemMaterialListPreview',
        name: 'computemMaterialListPreview',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/compute/preview/materialList'], resolve)
        },
        meta:{keepAlive:true}
      },
      { //预览核算单材料子级物料
        path: 'computemChilderMaterialPreview',
        name: 'computemChilderMaterialPreview',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/compute/preview/childerMaterial'], resolve)
        },
        meta:{keepAlive:true}
      },
      { //参考历史核算单
        path: 'computemReferTo',
        name: 'computemReferTo',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/compute/preview/referTo'], resolve)
        },
        meta:{keepAlive:true}
      },

      { //核算单详情
        path: 'compute/detail/:action/:compute_id',
        name: 'computeDetail',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/compute/detail'], resolve)
        },
        meta:{keepAlive:true}
      },
      { //核算单制品列表
        path: 'compute/cst_prod_list/:action',
        name: 'cst_prod_list',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/compute/cst_prod_list'], resolve)
        },
        meta:{keepAlive:true}
      },
      { //核算单明细零部件列表
        path: 'compute/cst_part_list/:action/:compute_id',
        name: 'cst_part_list',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/compute/cst_part_list'], resolve)
        },
        meta:{keepAlive:true}
      },
      { //核算单明细零部件详情
        path: 'compute/cst_part_detail/:action/:part_id',
        name: 'cst_part_detail',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/compute/cst_part_detail'], resolve)
        },
      },
      { //核算单明细零部件 子部件详情
        path: 'compute/cst_part_sub_detail/:action',
        name: 'cst_part_sub_detail',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/compute/cst_part_sub_detail'], resolve)
        },
      },
      { //核算单明细 加工项目列表
        path: 'compute/cst_process_list/:action/:compute_id',
        name: 'cst_process_list',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/compute/cst_process_list'], resolve)
        },
      },
      { //核算单明细 加工项目详情
        /*path: 'compute/cst_process_detail/:action/:process_detail',*/
        path: 'compute/cst_process_detail',
        name: 'cst_process_detail',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/compute/cst_process_detail'], resolve)
        },
      },


      //报价单
      { //报价单列表
        path: 'quote/list',
        name: 'quoteList',
        component: QuotesList,
      },
      { //报价单详情编辑
        path: 'quote/detail/:action/:quote_id',
        name: 'quoteDetail',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/quotes/detail'], resolve)
        },
      },
      { //报价单详情预览
        path: 'quotePreviewDetail',
        name: 'quotePreviewDetail',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/quotes/previewDetail'], resolve)
        },
      },
      { //参考历史报价单
        path: 'quote/associate',
        name: 'quoteAssociate',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/quotes/associate'], resolve)
        },
      },
      { //历史报价单列表
        path: 'quote/quoteHistory',
        name: 'quoteHistory',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/quotes/quoteHistory'], resolve)
        },
      },
      { //报价单详情预览
        path: 'quoteReferTo',
        name: 'quoteReferTo',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/quotes/referTo'], resolve)
        },
      },
      { //报价单行信息详情
        path: 'quote/line_info/:action/:quote_line_id',
        name: 'quote_line_info',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/quotes/quote_line_info'], resolve)
        },
      },
      { //报价单行信息制品列表
        path: 'quote/line_prods/:action/:cst_prod_list',
        name: 'quote_line_prods',
        component: function (resolve) {
          require(['@/views/customerOrder/quotes/quotes/quote_line_prods'], resolve)
        },
      },


      //定单
      { //客户定单列表
        path: 'order/list',
        name: 'orderList',
        component: orderList
      },
      { //参考历史定单
        path: 'order/associate',
        name: 'orderAssociate',
        component: function (resolve) {
          require(['@/views/customerOrder/orders/associate'], resolve)
        },
      },
      { //历史定单列表
        path: 'order/orderHistory',
        name: 'orderHistory',
        component: function (resolve) {
          require(['@/views/customerOrder/orders/orderHistory'], resolve)
        },
      },
      { //客户定单详情
        path: 'order/detail/:action/:project_id',
        name: 'orderDetail',
        component: function (resolve) {
          require(['@/views/customerOrder/orders/detail'], resolve)
        },
      },
      { //客户定单详情
        path: 'order/orderReferTo',
        name: 'orderReferTo',
        component: function (resolve) {
          require(['@/views/customerOrder/orders/referTo'], resolve)
        },
      },
      { //客户定单预览详情
        path: 'order/previewDetail',
        name: 'orderPreviewDetail',
        component: function (resolve) {
          require(['@/views/customerOrder/orders/previewDetail'], resolve)
        },
      },

      { //客户定单行信息详情
        path: 'order/lineInfo/:action/:line_id',
        name: 'orderLineInfo',
        component: function (resolve) {
          require(['@/views/customerOrder/orders/order_line_info'], resolve)
        },
      },

      { //客户定单行信息制品列表
        path: 'order/prdList/:action/:line_id',
        name: 'orderPrdList',
        component: function (resolve) {
          require(['@/views/customerOrder/orders/order_prd_list'], resolve)
        },
      },

      //定单管理

      { //定单管理列表
        path: 'manage/list',
        name: 'manageList',
        component: manageList
      },

      { //定单下达页
        path: 'manage/start/:order_line_id',
        name: 'manageStart',
        component: function (resolve) {
          require(['@/views/customerOrder/management/start'], resolve)
        },
      },
    ]
  },

  {
    path: '/purchase',   //采购管理
    name: 'purchase',
    component: Purchase,
    redirect: 'noredirect',
    children: [
      {
        //物料需求计划
        path: 'requirements',
        name: 'requirements',
        component: function (resolve) {
          require(['@/views/purchase/requirements/index'], resolve)
        }
      },
      {
        //物料需求计划----物料详情
        path: 'requirements/detail/:require_id',
        name: 'requirementDetail',
        component: function (resolve) {
          require(['@/views/purchase/requirements/detail'], resolve)
        }
      },
      {
        //询价单
        path: 'inquiry',
        name: 'inquiryList',
        component: function (resolve) {
          require(['@/views/purchase/inquiry/index'], resolve)
        }
      },
      {
        //询价单详情
        path: 'inquiry/detail/:action/:inquiry_id',
        name: 'inquiryDetail',
        component: function (resolve) {
          require(['@/views/purchase/inquiry/detail'], resolve)
        }
      },
      {
        //询价反馈单
        path: 'inquiry/feedback/:action/:inquiry_id',
        name: 'inquiryFeedback',
        component: function (resolve) {
          require(['@/views/purchase/inquiry/feedback'], resolve)
        }
      },
      {
        //采购单
        path: 'purchase',
        name: 'purchaseList',
        component: function (resolve) {
          require(['@/views/purchase/purchase/index'], resolve)
        }
      },
      {
        //采购单详情
        path: 'purchase/detail/:action/:purchase_id',
        name: 'purchaseDetail',
        component: function (resolve) {
          require(['@/views/purchase/purchase/detail'], resolve)
        }
      },
      {
        //请购单
        path: 'requisitions',
        name: 'requisitionList',
        component: function (resolve) {
          require(['@/views/purchase/requisitions/index'], resolve)
        }
      },
      {
        //请购单详情
        path: 'requisition/detail/:action/:request_id',
        name: 'requisitionDetail',
        component: function (resolve) {
          require(['@/views/purchase/requisitions/detail'], resolve)
        }
      },
      {
        //请购单行信息详情
        path: 'requisition/lineInfo/:action',
        name: 'requisitionLineInfo',
        component: function (resolve) {
          require(['@/views/purchase/requisitions/line_info'], resolve)
        }
      },
      {
        //历史请购单列表
        path: 'requisition/history',
        name: 'requisitionHistory',
        component: function (resolve) {
          require(['@/views/purchase/requisitions/history'], resolve)
        }
      },
      {
        //送货单(销售定单)
        path: 'sales',
        name: 'salesList',
        component: function (resolve) {
          require(['@/views/purchase/sales'], resolve)
        }
      },
    ]
  },
  {
    path: '/mats',   //物料管理
    name: 'mats',
    component: Mats,
    redirect: 'noredirect',
    children: [
      {
        path: 'bom/list', name: 'bomList', component: function (resolve) {
          require(['@/views/mats/bom'], resolve)
        }
      },  //bom列表
      {
        path: 'bom/nodes/:action/:node_id/:bom_id/:app_flag', name: 'bomNodes', component: function (resolve) {
          require(['@/views/mats/bom/nodes'], resolve)
        }
      },//子物料列表
      {
        path: 'bom/nodes/childNode', name: 'bomchildNodes', component: function (resolve) {
          require(['@/views/mats/bom/childNodes'], resolve)
        }
      },  //某bom下一则节点列表
      {
        path: 'bom/nodes/electrodeList/:bom_id/:node_id/:mat_type/:mat_no/:app_flag', name: 'electrodeList', component: function (resolve) {
          require(['@/views/mats/bom/electrodeList'], resolve)
        }
      },//电极子物料列表
      {
        path: 'bom/childNodeDetail/:action/:node_id/:mat_type/:app_flag', name: 'childNodeDetail', component: function (resolve) {
          require(['@/views/mats/bom/childNodeDetail'], resolve)
        }
      },
      {
        path: 'bom/nodeDetail/:action/:node_id/:matTypeName/:app_flag', name: 'bomNodeDetail', component: function (resolve) {
          require(['@/views/mats/bom/nodeDetail'], resolve)
        }
      },  //某节点物料详情


    ]
  },
  {
    path: '/storage',   //库存管理
    name: 'stroage',
    component: Stroage,
    redirect: 'noredirect',
    children: [
      {   //物料基础数据列表
        path: 'BasicDataList', name: 'BasicDataList', component: function (resolve) {
          require(['@/views/storage/baseInfo/BasicDataList'], resolve)
        }
      },
      {   //新增物料基础数据
        path: 'newMat', name: 'newMat', component: function (resolve) {
          require(['@/views/storage/baseInfo/newMat.vue'], resolve)
        }
      },
      {   //物料基础数据详情
        path: 'matDetaile', name: 'matDetaile', component: function (resolve) {
          require(['@/views/storage/baseInfo/matDetaile'], resolve)
        }
      },
      {   //库存基本信息
        path: 'baseInfo', name: 'storageBaseInfo', component: function (resolve) {
          require(['@/views/storage/baseInfo'], resolve)
        }
      },
      {   //仓库列表
        path: 'storageList', name: 'storageList', component: function (resolve) {
          require(['@/views/storage/baseInfo/storageList'], resolve)
        }
      },
      {   //仓库详情
        path: 'storageDetail/:action/:store_house_id', name: 'storageDetail', component: function (resolve) {
          require(['@/views/storage/baseInfo/storageDetail'], resolve)
        }
      },
      {   //物料类型列表
        path: 'matTypeList', name: 'matTypes', component: function (resolve) {
          require(['@/views/storage/baseInfo/matTypeList'], resolve)
        }
      },
      {   //物料类型详情
        path: 'matTypeDetail/:action/:matTypeDetail', name: 'matTypeDetail', component: function (resolve) {
          require(['@/views/storage/baseInfo/matTypeDetail'], resolve)
        }
      },
      //领料单
      {   //领料单列表
        path: 'applyMatBill/list', name: 'applyMatBills', component: function (resolve) {
          require(['@/views/storage/applyMatBill/list'], resolve)
        }
      },
      {   //领料单详情
        path: 'applyMatBill/detail/:action/:bill_id', name: 'applyMatBillDetail', component: function (resolve) {
          require(['@/views/storage/applyMatBill/detail'], resolve)
        }
      },
      {   //物料列表，支持搜索
        path: 'applyMatBill/matList', name: 'matList', component: function (resolve) {
          require(['@/views/storage/applyMatBill/matList'], resolve)
        }
      },
      //盘点
      {   //查询盘点单列表
        path: 'inventory/list', name: 'inventoryList', component: function (resolve) {
          require(['@/views/storage/inventory/list'], resolve)
        }
      },
      {   //新增盘点单
        path: 'inventory/detail/new', name: 'addInventory', component: function (resolve) {
          require(['@/views/storage/inventory/addInventory'], resolve)
        }
      },
      {   //盘点单详情
        path: 'inventory/detail', name: 'inventoryDetail', component: function (resolve) {
          require(['@/views/storage/inventory/inventoryDetail'], resolve)
        }
      },
      //物料入库
      // {   //入库单列表
      //   path: 'storagePush/list', name: 'storagePushList', component: function (resolve) {
      //     require(['@/views/storage/storagePush/list'], resolve)
      //   }
      // },
      {   //入库单列表
        path: 'storagePush/list', name: 'storagePushList', component: function (resolve) {
          require(['@/views/storage/storagePush/list'], resolve)
        }
      },
      {   //新建入库单
        path: 'storagePush/new', name: 'newSingle', component: function (resolve) {
          require(['@/views/storage/storagePush/new'], resolve)
        }
      },
      {   //入库单详情
        path: 'storagePush/singleDetail/:action/:bill_no/:type', name: 'singleDetail', component: function (resolve) {
          require(['@/views/storage/storagePush/singleDetail'], resolve)
        }
      },
      //物料出库
      {   //出库单列表
        path: 'storagePull/list', name: 'storagePullList', component: function (resolve) {
          require(['@/views/storage/storagePull/list'], resolve)
        }
      },
      {   //新建出库单
        path: 'storagePull/new', name: 'newStoragePull', component: function (resolve) {
          require(['@/views/storage/storagePull/new'], resolve)
        }
      },
      {   //出库单详情
        path: 'storagePull/singleDetail/:action/:bill_no/:type', name: 'StoragePullDetail', component: function (resolve) {
          require(['@/views/storage/storagePull/singleDetail'], resolve)
        }
      },
      //仓库物料报废
      {   //报废单列表
        path: 'scrap/list', name: 'scrapList', component: function (resolve) {
          require(['@/views/storage/scrap/list'], resolve)
        }
      },
      {   //报废单详情
        path: 'scrap/detail/:action/:scrap_id', name: 'scrapDetail', component: function (resolve) {
          require(['@/views/storage/scrap/detail'], resolve)
        }
      },
      //库存物料管理
      {   //新建物料管理
        path: 'management/new/:action', name: 'newManagement', component: function (resolve) {
          require(['@/views/storage/management/new'], resolve)
        }
      },
      {   //物料列表
        path: 'management/matList', name: 'managementMatList', component: function (resolve) {
          require(['@/views/storage/management/matList'], resolve)
        }
      },

      {   //凭证管理
        path: 'voucher/list', name: 'voucherList', component: function (resolve) {
          require(['@/views/storage/voucher/list'], resolve)
        }
      },
      {   //凭证详情
        path: 'voucher/Detail/:voucher_no', name: 'voucherDetail', component: function (resolve) {
          require(['@/views/storage/voucher/detail'], resolve)
        }
      },
    ]
  },
  {
    path: '/production',   //生产管理
    name: 'Production',
    component: Production,
    redirect: 'noredirect',
    children: [
      {   //工作日历
        path: 'shopCalendar', name: 'shopCalendar', component: function (resolve) {
          require(['@/views/production/shopCalendar/index'], resolve)
        }
      },
      {   //班次详情
        path: 'shift/:action/:shift_id', name: 'shift', component: function (resolve) {
          require(['@/views/production/shopCalendar/shift'], resolve)
        }
      },
      {   //日历详情
        path: 'calendar/:action/:calendar_id', name: 'calendar', component: function (resolve) {
          require(['@/views/production/shopCalendar/calendar'], resolve)
        }
      },
      {   //点检列表
        path: 'check', name: 'check', component: function (resolve) {
          require(['@/views/production/check/index'], resolve)
        }
      },
      {   //点检
        path: 'checkDetail', name: 'checkDetail', component: function (resolve) {
          require(['@/views/production/check/detail'], resolve)
        }
      },
      {   //点检记录
        path: 'checkRecord', name: 'checkRecord', component: function (resolve) {
          require(['@/views/production/check/record'], resolve)
        }
      },
      {   //基础工序列表
        path: 'procedure', name: 'procedure', component: function (resolve) {
          require(['@/views/production/procedure/index'], resolve)
        }
      },
      {   //工序详情
        path: 'procedureDetaile', name: 'procedureDetaile', component: function (resolve) {
          require(['@/views/production/procedure/detaile'], resolve)
        }
      },
      {   //部门列表
        path: 'departmentList', name: 'departmentList', component: function (resolve) {
          require(['@/views/production/procedure/departmentList'], resolve)
        }
      },
      {   //工艺模板列表
        path: 'procTemplate', name: 'procTemplate', component: function (resolve) {
          require(['@/views/production/procTemplate/index'], resolve)
        }
      },
      {   //工艺模版详情
        path: 'templateDetaile', name: 'templateDetaile', component: function (resolve) {
          require(['@/views/production/procTemplate/detaile'], resolve)
        }
      },
      {   //工艺模板类型列表
        path: 'templateTypeList', name: 'templateTypeList', component: function (resolve) {
          require(['@/views/production/procTemplate/typeList'], resolve)
        }
      },
      {   //工艺模板添加工序
        path: 'templateAddProc', name: 'templateAddProc', component: function (resolve) {
          require(['@/views/production/procTemplate/addProcedure'], resolve)
        }
      },
      {   //工艺路线
        path: 'processRoute', name: 'processRoute', component: function (resolve) {
          require(['@/views/production/processRoute'], resolve)
        }
      },
      {   //搜索创建工艺路线
        path: 'searchList', name: 'searchList', component: function (resolve) {
          require(['@/views/production/processRoute/searchList'], resolve)
        }
      },
      {   //物料列表
        path: 'processRoute/matList', name: 'mateList', component: function (resolve) {
          require(['@/views/production/processRoute/matList'], resolve)
        }
      },
      {   //物料节点列表
        path: 'processRoute/nodesList', name: 'nodesList', component: function (resolve) {
          require(['@/views/production/processRoute/nodesList'], resolve)
        }
      },
      {   //工艺路线详情
        path: 'processRouteDetaile', name: 'processRouteDetaile', component: function (resolve) {
          require(['@/views/production/processRoute/detaile'], resolve)
        }
      },
      {   //添加工序
        path: 'addProcedure', name: 'addProcedure', component: function (resolve) {
          require(['@/views/production/procTemplate/addProcedure'], resolve)
        }
      },
      {   //排产列表
        path: 'schedulList', name: 'schedulList', component: function (resolve) {
          require(['@/views/production/processRoute/schedulList'], resolve)
        }
      },
      {   //排产
        path: 'scheduling', name: 'scheduling', component: function (resolve) {
          require(['@/views/production/processRoute/Scheduling'], resolve)
        }
      },
      {   //排产预览
        path: 'schedulingPreview', name: 'schedulingPreview', component: function (resolve) {
          require(['@/views/production/processRoute/preview'], resolve)
        }
      },
      {   //报工
        path: 'work', name: 'work', component: function (resolve) {
          require(['@/views/production/work'], resolve)
        }
      },
      {   //任务列表
        path: 'work/taskList', name: 'taskList', component: function (resolve) {
          require(['@/views/production/work/taskList'], resolve)
        }
      },
      {   //加工信息
        path: 'work/workInfo', name: 'workInfo', component: function (resolve) {
          require(['@/views/production/work/workInfo'], resolve)
        }
      },
      {   //任务领取
        path: 'getTask', name: 'getTask', component: function (resolve) {
          require(['@/views/production/work/getTask'], resolve)
        }
      },
      {   //任务指派人员列表
        path: 'getTask/persList', name: 'persList', component: function (resolve) {
          require(['@/views/production/work/personnelList'], resolve)
        }
      },
      {   //任务指派设备列表
        path: 'getTask/devList', name: 'devList', component: function (resolve) {
          require(['@/views/production/work/distributionList'], resolve)
        }
      },
      //模具领料
      {   //领料单列表
        path: 'MaterialRequisition', name: 'MaterialRequisition', component: function (resolve) {
          require(['@/views/production/MaterialRequisition'], resolve)
        }
      },
      {   //新建领料单
        path: 'MaterialRequisition/detaile', name: 'MaterialRequisitionDetaile', component: function (resolve) {
          require(['@/views/production/MaterialRequisition/detaile'], resolve)
        }
      },
      {   //新建领料单
        path: 'MaterialRequisition/MaterialList', name: 'MaterialList', component: function (resolve) {
          require(['@/views/production/MaterialRequisition/matList'], resolve)
        }
      },



    ]
  },
  {
    path: '/qualityCheck',   //质量检测
    name: 'qualityCheck',
    component: Production,
    redirect: 'noredirect',
    children:[
      {   //来料质检
        path: 'MaterialQC', name: 'MaterialQC', component: function (resolve) {
          require(['@/views/qualityCheck/MaterialQC'], resolve)
        }
      },//Quality check
      {   //新建质检单
        path: 'newMaterialQC', name: 'newMaterialQC', component: function (resolve) {
          require(['@/views/qualityCheck/MaterialQC/new'], resolve)
        }
      },
      {   //来料质检单
        path: 'qcDetaile', name: 'qcDetaile', component: function (resolve) {
          require(['@/views/qualityCheck/MaterialQC/detaile'], resolve)
        }
      },
      {   //工序质检
        path: 'ProcedureQC', name: 'ProcedureQC', component: function (resolve) {
          require(['@/views/qualityCheck/ProcedureQC'], resolve)
        }
      },
      {   //新建工序质检
        path: 'newProcedureQC', name: 'newProcedureQC', component: function (resolve) {
          require(['@/views/qualityCheck/ProcedureQC/new'], resolve)
        }
      },
      {   //工序质检单
        path: 'ProcedureQCdetaile', name: 'ProcedureQCdetaile', component: function (resolve) {
          require(['@/views/qualityCheck/ProcedureQC/detaile'], resolve)
        }
      },
      {   //审批流程
        path: 'workflowOpinion', name: 'workflowOpinion', component: function (resolve) {
          require(['@/views/qualityCheck/MaterialQC/workflowOpinion'], resolve)
        }
      },

    ]
  },
  {
    path: '/statistics',   //统计页面
    name: 'statistics',
    component: statistics,
    redirect: 'noredirect',
    children:[
      {
        path: 'chart',
        name: 'statisticsChart',
        component: function (resolve) {
          require(['@/views/statistics/chart'], resolve)
        }
      }
    ]
  }
]

export default routes
