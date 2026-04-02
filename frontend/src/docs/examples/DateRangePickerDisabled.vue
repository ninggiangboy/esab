<script setup lang="ts">
import { parseDate } from '@internationalized/date'

import UiDateRangePicker from '@/components/ui/UiDateRangePicker.vue'
</script>

<template>
  <UiDateRangePicker
    disabled
    class="opacity-80"
    :default-value="{
      start: parseDate('2024-01-01'),
      end: parseDate('2024-01-31'),
    }"
  />
</template>
