export const setLoginUser = (state, user) => {
    sessionStorage.setItem("loginUser", JSON.stringify(user));
    sessionStorage.setItem("isLogin", true);
    state.loginUser = user;
    state.isLogin = true;
  };
  export const logout = (state) => {
    sessionStorage.removeItem("loginUser");
    sessionStorage.removeItem("isLogin");
    state.loginUser = {};
    state.isLogin = false;
  };