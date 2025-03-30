<template>
<h1>Das aktuelle Mitfahrangebot</h1>

<div class="main-content">
    <table>
        <thead>
            <tr>
                <th>Abfahrt</th>
                <th>von</th>
                <th>nach</th>
                <th>Entfernung</th>
                <th>Plätze</th>
                <th>Frei</th>
                <th>Preis</th>
            </tr>
        </thead>
        <tbody>
            <TourListenZeile v-for="tour in touren" :key="tour.id" :tour="tour" />
        </tbody>
    </table>
</div>

</template>

<script setup lang="ts">
//bezieht daten aus einem Array von ITourDTD, das über Property touren hereinereicht werden kann, wie sie in tourenListeView sehen können.

import { defineProps } from 'vue'
import TourListenZeile from './TourListenZeile.vue' //Ich will die liste aus mehreren Zeilen aufbauen
import type { ITourDTD } from '@/stores/ITourDTD'
defineProps<{
  touren: ITourDTD[]
}>()

</script>

<style scoped>
@import './../../assets/style.css';
</style>