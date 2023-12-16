import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import store from "@/store/index.js";
import {connect} from "@/ws";

connect();

createApp(App)
    .use(store)
    .mount('#app')
