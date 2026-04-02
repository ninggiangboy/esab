<script setup lang="ts">
import { parseDate } from '@internationalized/date'
import { ref } from 'vue'

import UiRangeCalendar from '@/components/ui/UiRangeCalendar.vue'

const range = ref({
  start: parseDate('2024-03-01'),
  end: parseDate('2024-03-20'),
})
</script>

<template>
  <div class="space-y-2">
    <UiRangeCalendar v-model="range" />
    <p class="m-0 text-xs text-muted-foreground">
      {{ range.start?.toString() ?? '—' }} — {{ range.end?.toString() ?? '—' }}
    </p>
  </div>
</template>
