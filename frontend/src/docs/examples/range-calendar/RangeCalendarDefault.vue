<script setup lang="ts">
import { getLocalTimeZone, today } from '@internationalized/date'
import { BsRangeCalendar } from '@/ui/components/calendar'

const t = today(getLocalTimeZone())
const def = { start: t, end: t.add({ days: 10 }) }
</script>

<template>
  <BsRangeCalendar :default-value="def" />
</template>
