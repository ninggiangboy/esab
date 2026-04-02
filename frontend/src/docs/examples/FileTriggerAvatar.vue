<script setup lang="ts">
import { Upload } from 'lucide-vue-next'
import { ref, watch } from 'vue'

import UiAvatar from '@/components/ui/UiAvatar.vue'
import UiButton from '@/components/ui/UiButton.vue'

const inputRef = ref<HTMLInputElement | null>(null)
const previewUrl = ref<string | null>(null)
const file = ref<File | null>(null)

watch(file, (f) => {
  if (previewUrl.value) URL.revokeObjectURL(previewUrl.value)
  previewUrl.value = f ? URL.createObjectURL(f) : null
})

function onPick(ev: Event) {
  const input = ev.target as HTMLInputElement
  const f = input.files?.[0] ?? null
  file.value = f
}
</script>

<template>
  <div class="flex items-center gap-3 text-sm">
    <UiAvatar :src="previewUrl ?? undefined" alt="Avatar" fallback="CN" class="size-20! text-lg" />
    <input ref="inputRef" type="file" accept=".png,.jpg,.jpeg" class="sr-only" @change="onPick" />
    <UiButton variant="outline" type="button" @click="inputRef?.click()">
      <Upload class="size-4" /> Select a file
    </UiButton>
  </div>
</template>
