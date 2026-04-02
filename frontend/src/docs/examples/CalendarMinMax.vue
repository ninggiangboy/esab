<script setup lang="ts">
import { parseDate } from '@internationalized/date'

import UiCalendar from '@/components/ui/UiCalendar.vue'

const min = parseDate('2024-01-01')
const max = parseDate('2024-12-31')
</script>

<template>
  <UiCalendar :min-value="min" :max-value="max" />
</template>
