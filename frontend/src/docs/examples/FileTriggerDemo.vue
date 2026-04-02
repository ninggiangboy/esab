<script setup lang="ts">
import { Upload } from 'lucide-vue-next'
import { ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'

const fileName = ref('')
const inputRef = ref<HTMLInputElement | null>(null)

function onPick(ev: Event) {
  const input = ev.target as HTMLInputElement
  fileName.value = input.files?.[0]?.name ?? ''
}
</script>

<template>
  <div class="flex items-center gap-2 text-sm">
    <input ref="inputRef" type="file" class="sr-only" @change="onPick" />
    <UiButton variant="outline" type="button" @click="inputRef?.click()">
      <Upload class="size-4" /> Select a file
    </UiButton>
    <span v-if="fileName" class="text-muted-foreground">{{ fileName }}</span>
  </div>
</template>
