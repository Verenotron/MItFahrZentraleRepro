<template>
<h1>Das aktuelle Mitfahrangebot</h1>

<thead>
    <tr>
        <th>TourID</th>
        <th>TourName</th>
        <th>TourDescription</th>
        <th>TourPrice</th>
    </tr>
</thead>
<tbody>
    <table>
        <TourListenZeile v-for="tour in touren" :key="tour.id" :tour="tour" />
    </table>
</tbody>

<!-- <div v-if="touren.length > 0">
    <ul>
        <li v-for="tour in touren" :key="tour.id">
            <h3>{{ tour.name }}</h3>
            <p>{{ tour.description }}</p>
            <p>Preis: {{ tour.price }} €</p>
        </li>
    </ul>
</div>
<div v-else>Keine Touren Verfügbar</div> -->
</template>

<script setup lang="ts">
//bezieht daten aus einem Array von ITourDTD, das über Property touren hereinereicht werden kann, wie sie in tourenListeView sehen können.

import { defineProps } from 'vue'
import TourListenZeile from './TourListenZeile.vue' //Ich will die liste aus mehreren Zeilen aufbauen
import type { ITourDTD } from '@/stores/ITourDTD'
//import TourListenZeile from './TourListenZeile.vue'
//import { touren } from '@/views/TourenListeView.vue'
defineProps<{
  touren: ITourDTD[]
}>()

</script>