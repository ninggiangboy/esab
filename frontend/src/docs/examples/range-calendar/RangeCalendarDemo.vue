<script setup lang="ts">
import { getLocalTimeZone, today } from '@internationalized/date'
import RangeCalendarPanel from '@/docs/examples/_internal/RangeCalendarPanel.vue'

const t = today(getLocalTimeZone())
const def = { start: t, end: t.add({ days: 10 }) }
</script>

<template>
  <RangeCalendarPanel :default-value="def" />
</template>
