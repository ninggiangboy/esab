<script setup lang="ts">
import { ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'

const inputRef = ref<HTMLInputElement | null>(null)
const files = ref<File[]>([])
const error = ref('')

function onChange(ev: Event) {
  const input = ev.target as HTMLInputElement
  error.value = ''
  if (!input.files?.length) return
  const next: File[] = []
  for (const f of input.files) {
    const ok = /\.(pdf|docx|png|csv)$/i.test(f.name)
    if (!ok) {
      error.value = 'Only pdf, docx, png, csv'
      input.value = ''
      return
    }
    if (files.value.length + next.length >= 3) {
      error.value = 'Max 3 files'
      break
    }
    next.push(f)
  }
  files.value = [...files.value, ...next]
}

function clear() {
  files.value = []
}
</script>

<template>
  <div class="space-y-2">
    <input
      ref="inputRef"
      type="file"
      multiple
      accept=".pdf,.docx,.png,.csv"
      class="sr-only"
      @change="onChange"
    />
    <UiButton variant="outline" type="button" @click="inputRef?.click()"
      >Choose (validated)</UiButton
    >
    <p v-if="error" class="m-0 text-sm text-destructive">{{ error }}</p>
    <ul v-if="files.length" class="m-0 list-none space-y-1 p-0 text-sm text-muted-foreground">
      <li v-for="(f, i) in files" :key="`${f.name}-${i}`">{{ f.name }}</li>
    </ul>
    <UiButton v-if="files.length" variant="ghost" size="sm" type="button" @click="clear"
      >Clear</UiButton
    >
  </div>
</template>
