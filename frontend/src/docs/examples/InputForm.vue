<script setup lang="ts">
import { reactive, ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'

const form = reactive({ email: '', name: '' })
const message = ref('')

function submit() {
  message.value = JSON.stringify({ ...form })
}
</script>

<template>
  <form class="max-w-sm space-y-3" @submit.prevent="submit">
    <div class="grid gap-1.5">
      <UiLabel for="f-name">Name</UiLabel>
      <UiInput
        id="f-name"
        :value="form.name"
        @input="form.name = ($event.target as HTMLInputElement).value"
      />
    </div>
    <div class="grid gap-1.5">
      <UiLabel for="f-email">Email</UiLabel>
      <UiInput
        id="f-email"
        type="email"
        :value="form.email"
        @input="form.email = ($event.target as HTMLInputElement).value"
      />
    </div>
    <UiButton type="submit">Submit</UiButton>
    <p v-if="message" class="m-0 text-xs text-muted-foreground">{{ message }}</p>
  </form>
</template>
