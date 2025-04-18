<template>
   <header class="kopf">
        <nav class="header-content">
            <div class="div-header">
                <!-- <a class="text" href="@{/benutzer}">Benutzer</a>
                <a class="text" href="@{/ort}">Orte</a> -->
                <RouterLink class="text" to="/touren" @click="loescheInfo()">{{ $t('touren') }}</RouterLink>
            </div>
            <div class="div-header">
                <a class="text" href="${sprache}'">{{ $t('sprachen') }}: </a>
                <button @click="sprachWechsel('de')">DE</button>
                <button @click="sprachWechsel('en')">EN</button>
                <button @click="sprachWechsel('nl')">NL</button>
                <button @click="userLogout()">{{ $t('abmelden') }}</button>  
            </div>      
        </nav>
        
    </header>

    <div id="app">

        <div v-if="info" class="info-box">
        <div class="infobox-header">Obacht! <button @click="loescheInfo()" class="close-btn">x</button></div> 
        <div class="infobox-content">{{ info }}</div>
    </div>
    <!-- <button @click="setzeInfo('Dies ist eine Nachricht.')">Hallo</button> @click ist eine kurzschreibweise für v-on:click -->
        <RouterView />
    </div>
    
    <footer class="footer-content">
        <nav>
            <a class="text" href="#">{{ $t('impressum') }}</a>
            <a class="text" href="#">{{ $t('kontakt') }}</a>
        </nav>

        <p>{{ loginState.username }}</p>
    </footer>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useLogin } from '@/composables/useLogin.ts'
import { useInfo } from './composables/useInfo.ts'
import { RouterView } from 'vue-router'
import { onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
const { locale } = useI18n()

const {info, loescheInfo, setzeInfo} = useInfo() //useInfo wird einmal aufgerufen und benötigte Teile werden extrahiert mit Destructing.
onMounted(() => loescheInfo())
console.log("Komponente neu geladen.")

const { loginState, login, logout } = useLogin();
const router = useRouter();

function userLogout(){
    logout()
    router.push("/login")
}

function sprachWechsel(sprache : string){
    locale.value = sprache
}

</script>

<style scoped>
@import './assets/style.css';
</style>

