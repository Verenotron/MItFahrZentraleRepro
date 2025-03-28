<script setup lang="ts"></script>

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

        <!-- <p class="error-border" th:if="${info}" th:text="${info}"></p> -->
        
    </header>

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
