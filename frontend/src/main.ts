//import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { createI18n } from 'vue-i18n'
import de from './locales/de.json'
import en from './locales/en.json'
import nl from './locales/nl.json'

import App from './App.vue'
import router from './router'

const app = createApp(App)
const i18n = createI18n({
    locale: 'de',
    messages: { de, en, nl }})

app.use(i18n)

app.use(createPinia())
app.use(router)

app.mount('#app')
