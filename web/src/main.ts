import { createApp } from 'vue'
import './style.css'
import 'element-plus/dist/index.css'
import ElementPlus from 'element-plus'
import App from './App.vue'
import router from './route'

createApp(App).use(router).use(ElementPlus).mount('#app')
