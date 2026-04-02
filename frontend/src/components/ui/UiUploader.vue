<script setup lang="ts">
import { ref } from 'vue'

import { cn } from '@/lib/utils'

import UiButton from './UiButton.vue'

const fileRef = ref<HTMLInputElement | null>(null)
const files = ref<File[]>([])

function onFiles(e: Event) {
  const input = e.target as HTMLInputElement
  if (!input.files) return
  files.value = [...files.value, ...input.files]
}

function remove(i: number) {
  files.value.splice(i, 1)
}
</script>

<template>
  <div :class="cn('space-y-2', $attrs.class as string | undefined)">
    <div class="flex items-center gap-2">
      <input ref="fileRef" type="file" multiple class="sr-only" @change="onFiles" />
      <UiButton variant="outline" type="button" @click="fileRef?.click()">Choose files</UiButton>
    </div>
    <ul v-if="files.length" class="m-0 list-none space-y-1 p-0 text-sm text-muted-foreground">
      <li
        v-for="(f, i) in files"
        :key="`${f.name}-${i}`"
        class="flex items-center justify-between gap-2"
      >
        <span class="truncate">{{ f.name }}</span>
        <UiButton variant="ghost" size="iconSm" type="button" @click="remove(i)">×</UiButton>
      </li>
    </ul>
    <slot />
  </div>
</template>
