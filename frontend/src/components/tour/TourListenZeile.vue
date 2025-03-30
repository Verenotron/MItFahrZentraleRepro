<template>

    <tr>
        <td>{{tour.abfahrt}}</td>
        <td>{{tour.von}}</td>
        <td>{{tour.nach}}</td>
        <td>{{tour.entfernung}}</td>
        <td>{{tour.plaetze}}</td>
        <td>{{frei}}</td>
        <td>{{tour.preis}}</td>
        <td class="noBorderElement"> <button>Details</button></td>
    </tr>

</template>

<script setup lang="ts">
import { defineProps, computed } from 'vue'
import type { ITourDTD } from '@/stores/ITourDTD'

const frei = computed(() => props.tour.plaetze - props.tour.buchungen)

const props = defineProps<{
  tour: ITourDTD
}>()

console.log("Daten :: " , props.tour)

</script>
<style scoped>
@import './../../assets/style.css';
</style>