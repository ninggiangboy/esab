<script setup lang="ts">
import { parseDate } from '@internationalized/date'

import UiRangeCalendar from '@/components/ui/UiRangeCalendar.vue'

const min = parseDate('2024-01-01')
const max = parseDate('2024-06-30')
</script>

<template>
  <UiRangeCalendar :min-value="min" :max-value="max" />
</template>
