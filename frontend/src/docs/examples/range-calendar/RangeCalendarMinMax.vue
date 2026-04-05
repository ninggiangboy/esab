<script setup lang="ts">
import { getLocalTimeZone, today } from '@internationalized/date'
import { BsRangeCalendar } from '@/ui/components/calendar'

const min = today(getLocalTimeZone())
const max = min.add({ days: 10 })
</script>

<template>
  <BsRangeCalendar :min-value="min" :max-value="max" />
</template>
