<template>

    <tr>
        <td>{{tour.id}}</td>
        <td>{{tour.name}}</td>
        <td>{{tour.description}}</td>
        <td>{{tour.price}}</td>
    </tr>

</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import type { ITourDTD } from '@/stores/ITourDTD'

const props = defineProps<{
  tour: ITourDTD
}>()

console.log("Daten :: " , props.tour)

</script>