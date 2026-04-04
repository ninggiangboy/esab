<script setup lang="ts">
import type { DateValue } from '@internationalized/date'
import { ref } from 'vue'
import SingleCalendarPanel from '@/docs/examples/_internal/SingleCalendarPanel.vue'

const value = ref<DateValue | undefined>()
</script>

<template>
  <div class="flex flex-col gap-2">
    <SingleCalendarPanel v-model="value" />
    <pre class="rounded-md border bg-background-secondary p-2 font-mono text-xs">{{ value ? value.toString() : 'No value' }}</pre>
  </div>
</template>
