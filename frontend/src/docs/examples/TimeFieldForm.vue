<script setup lang="ts">
import { reactive, ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'

const form = reactive({ t: '09:00' })
const message = ref('')

function submit() {
  message.value = `Time: ${form.t}`
}
</script>

<template>
  <form class="max-w-xs space-y-3" @submit.prevent="submit">
    <div class="grid gap-1.5">
      <UiLabel for="tf-f">Start time</UiLabel>
      <UiInput
        id="tf-f"
        type="time"
        :value="form.t"
        @input="form.t = ($event.target as HTMLInputElement).value"
      />
    </div>
    <UiButton type="submit">Submit</UiButton>
    <p v-if="message" class="m-0 text-xs text-muted-foreground">{{ message }}</p>
  </form>
</template>
