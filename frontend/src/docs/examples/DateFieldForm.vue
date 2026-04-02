<script setup lang="ts">
import { reactive, ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiDateInput from '@/components/ui/UiDateInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'

const form = reactive({ date: '' })
const message = ref('')

function submit() {
  message.value = form.date ? `Date: ${form.date}` : 'Pick a date'
}
</script>

<template>
  <form class="max-w-xs space-y-3" @submit.prevent="submit">
    <div class="grid gap-1.5">
      <UiLabel for="df-d">Event date</UiLabel>
      <UiDateInput id="df-d" v-model="form.date" name="event_date" />
    </div>
    <UiButton type="submit">Submit</UiButton>
    <p v-if="message" class="m-0 text-xs text-muted-foreground">{{ message }}</p>
  </form>
</template>
