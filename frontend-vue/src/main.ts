import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { createAuth0 } from '@auth0/auth0-vue'

import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(
    createAuth0({
        domain: 'dev-sethaker.us.auth0.com',
        clientId: "Ud60sEpQ4lzv5RVj3TZQgTrmv9A5GCID",
        authorizationParams: {
            redirect_uri: window.location.origin,
            audience: "http://localhost:8080"
        }
    })
)

app.mount('#app')
