<script setup lang="ts">
import { ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiTextarea from '@/components/ui/UiTextarea.vue'

const text = ref('')
</script>

<template>
  <div class="flex w-full max-w-md flex-col gap-2">
    <UiTextarea
      :value="text"
      placeholder="Compose…"
      class="min-h-[100px]"
      @input="text = ($event.target as HTMLTextAreaElement).value"
    />
    <UiButton class="self-end" type="button">Send</UiButton>
  </div>
</template>
