<script setup lang="ts">
import { reactive, ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
import UiTextarea from '@/components/ui/UiTextarea.vue'

const form = reactive({ note: '' })
const message = ref('')

function submit() {
  message.value = `Saved: ${form.note.slice(0, 80)}${form.note.length > 80 ? '…' : ''}`
}
</script>

<template>
  <form class="max-w-md space-y-3" @submit.prevent="submit">
    <div class="grid gap-1.5">
      <UiLabel for="ta-note">Note</UiLabel>
      <UiTextarea
        id="ta-note"
        :value="form.note"
        class="min-h-[80px]"
        @input="form.note = ($event.target as HTMLTextAreaElement).value"
      />
    </div>
    <UiButton type="submit">Save</UiButton>
    <p v-if="message" class="m-0 text-xs text-muted-foreground">{{ message }}</p>
  </form>
</template>
