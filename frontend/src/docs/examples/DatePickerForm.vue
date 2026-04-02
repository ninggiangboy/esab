<script setup lang="ts">
import { ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiDatePicker from '@/components/ui/UiDatePicker.vue'
import UiLabel from '@/components/ui/UiLabel.vue'

const picked = ref<unknown>()

const message = ref('')

function submit() {
  message.value =
    picked.value != null && String(picked.value) !== ''
      ? `Date: ${String(picked.value)}`
      : 'Pick a date'
}
</script>

<template>
  <form class="max-w-md space-y-3" @submit.prevent="submit">
    <div class="grid gap-1.5">
      <UiLabel for="dp-f">Schedule</UiLabel>
      <UiDatePicker id="dp-f" v-model="picked" name="schedule_date" />
    </div>
    <UiButton type="submit">Submit</UiButton>
    <p v-if="message" class="m-0 text-xs text-muted-foreground">{{ message }}</p>
  </form>
</template>
