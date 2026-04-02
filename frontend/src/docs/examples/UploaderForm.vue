<script setup lang="ts">
import { reactive, ref } from 'vue'
import { toast } from 'vue-sonner'

import UiButton from '@/components/ui/UiButton.vue'
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
import UiUploader from '@/components/ui/UiUploader.vue'

const form = reactive({ name: '' })
const submitted = ref('')

function submit() {
  submitted.value = form.name || '(no name)'
  toast.message('Submitted', { description: submitted.value })
}
</script>

<template>
  <form class="w-full max-w-md space-y-4" @submit.prevent="submit">
    <div class="grid gap-1.5">
      <UiLabel for="up-name">Name</UiLabel>
      <UiInput
        id="up-name"
        :value="form.name"
        @input="form.name = ($event.target as HTMLInputElement).value"
      />
    </div>
    <div class="grid gap-1.5">
      <UiLabel>Attachments</UiLabel>
      <UiUploader />
    </div>
    <UiButton type="submit">Submit</UiButton>
  </form>
</template>
