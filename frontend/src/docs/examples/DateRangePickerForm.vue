<script setup lang="ts">
import { ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiDateRangePicker from '@/components/ui/UiDateRangePicker.vue'
import UiLabel from '@/components/ui/UiLabel.vue'

const range = ref({ start: undefined, end: undefined })

const message = ref('')

function submit() {
  const r = range.value
  const s = r.start != null ? String(r.start) : ''
  const e = r.end != null ? String(r.end) : ''
  message.value = `${s} → ${e}`
}
</script>

<template>
  <form class="max-w-md space-y-4" @submit.prevent="submit">
    <div class="grid gap-1.5">
      <UiLabel for="drf-range">Dates</UiLabel>
      <UiDateRangePicker id="drf-range" v-model="range" name="trip_range" />
    </div>
    <UiButton type="submit">Submit</UiButton>
    <p v-if="message" class="m-0 text-xs text-muted-foreground">{{ message }}</p>
  </form>
</template>
