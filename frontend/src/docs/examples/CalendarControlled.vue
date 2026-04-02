<script setup lang="ts">
import { parseDate } from '@internationalized/date'
import { ref } from 'vue'

import UiCalendar from '@/components/ui/UiCalendar.vue'

const selected = ref(parseDate('2024-06-15'))
</script>

<template>
  <div class="space-y-2">
    <UiCalendar v-model="selected" />
    <p class="m-0 text-xs text-muted-foreground">Model: {{ selected?.toString() ?? '—' }}</p>
  </div>
</template>
