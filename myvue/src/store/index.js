import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);
//创建Vuex实例对象
const store = new Vuex.Store({
    state: {
        currentPathName:''
    },
    mutations: {
        setPath (state){
            state.currentPathName =localStorage.getItem("currentPathName")
        }
    }
})
export default store;

