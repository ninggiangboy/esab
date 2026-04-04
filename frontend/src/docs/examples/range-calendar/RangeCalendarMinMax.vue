<script setup lang="ts">
import { getLocalTimeZone, today } from '@internationalized/date'
import RangeCalendarPanel from '@/docs/examples/_internal/RangeCalendarPanel.vue'

const min = today(getLocalTimeZone())
const max = min.add({ days: 10 })
</script>

<template>
  <RangeCalendarPanel :min-value="min" :max-value="max" />
</template>
