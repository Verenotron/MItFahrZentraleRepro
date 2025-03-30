<template>

    <thead> <!--thead definiet Kopfbeeich einer Tabelle-->
        <tr>
            <th>{{tour.id}}</th>
            <th>{{tour.name}}</th>
            <th>{{tour.description}}</th>
            <th>{{tour.price}}</th>
        </tr>
    </thead>

</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import type { ITourDTD } from '@/stores/ITourDTD'

const props = defineProps<{
  tour: ITourDTD
}>()

console.log("Daten :: " , props.tour)

</script>