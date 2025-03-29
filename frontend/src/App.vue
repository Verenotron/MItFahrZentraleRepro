<script setup lang="ts">
import { ref } from 'vue'
const info = ref('Dies ist eine Nachricht'); //ref() erzeugt reaktive Variablen(dh. man kann den Wert per UI ändern und Vue bemerkt das). Mit info.value kann man auf den String zugreifen.

</script>

<template>
   <header class="kopf">
        <nav class="header-content">
            <div class="div-header">
                <a class="text" href="@{/benutzer}">Benutzer</a>
                <a class="text" href="@{/ort}">Orte</a>
                <a class="text" href="@{/tour}">Touren</a>
            </div>
            <div class="div-header">
                <a class="text" href="${sprache}'" th:text="#{sprachen} + ' : '"></a>
                <a class="text" href="@{?sprache=de}">DE</a>
                <a class="text" href="@{?sprache=en}">EN</a>
                <a class="text" href="@{?sprache=nl}">NL</a>
            </div>            
            
        </nav>
        
    </header>

    <div v-if="info" class="info-box">
        <div class="infobox-header">Obacht! <button @click="info = ''" class="close-btn">x</button></div> 
        <div class="infobox-content">{{ info  }}</div>
    </div>
    <button @click="info='Dies ist eine Nachricht'">Hallo</button>
    
    <footer class="footer-content">
        <nav>
            <a class="text" href="#">Imressum</a>
            <a class="text" href="#">Kontakt</a>
        </nav>
    </footer>
</template>

<style scoped>
@import './assets/style.css';
</style>
