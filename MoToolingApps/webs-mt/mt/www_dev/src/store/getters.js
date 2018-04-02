const getters = {
  token: state => state.user.token,
  uid: state => state.user.uid,
  // permission_routers: state => state.permission.routers,
  // addRouters: state => state.permission.addRouters,
  userinfo: state => state.user.userinfo,
  chosenCompany: state => state.user.chosenCompany,
  activeIndex: state => state.app.activeIndex,
  hideNavbar: state => state.app.hideNavbar,
  baseURL:state => state.app.baseURL
};
export default getters
