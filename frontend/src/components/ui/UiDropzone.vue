<script setup lang="ts">
import { ref } from 'vue'

import { cn } from '@/lib/utils'

const emit = defineEmits<{
  files: [files: FileList | null]
}>()

const inputRef = ref<HTMLInputElement | null>(null)

function onChange(e: Event) {
  const input = e.target as HTMLInputElement
  emit('files', input.files)
  input.value = ''
}

function open() {
  inputRef.value?.click()
}
</script>

<template>
  <button
    type="button"
    :class="
      cn(
        'border border-dashed border-border rounded-lg p-8 w-full text-center text-sm text-muted-foreground hover:bg-background-secondary transition-colors',
        $attrs.class as string | undefined,
      )
    "
    @click="open"
  >
    <input ref="inputRef" type="file" class="sr-only" multiple v-bind="$attrs" @change="onChange" />
    <slot>Drop files here or click to browse</slot>
  </button>
</template>
