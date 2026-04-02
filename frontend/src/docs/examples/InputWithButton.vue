<script setup lang="ts">
import { ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'

const email = ref('')
</script>

<template>
  <form class="flex w-full flex-col gap-2 sm:max-w-md" @submit.prevent>
    <UiLabel for="in-sub">Subscribe</UiLabel>
    <div class="flex gap-2">
      <UiInput
        id="in-sub"
        :value="email"
        class="min-w-0 flex-1"
        type="email"
        placeholder="Email"
        @input="email = ($event.target as HTMLInputElement).value"
      />
      <UiButton type="submit">Join</UiButton>
    </div>
  </form>
</template>
