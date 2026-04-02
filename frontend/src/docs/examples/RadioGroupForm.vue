<script setup lang="ts">
import { ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
import UiRadioGroup from '@/components/ui/UiRadioGroup.vue'
import UiRadioItem from '@/components/ui/UiRadioItem.vue'

const plan = ref('pro')
const message = ref('')

function submit() {
  message.value = `Selected plan: ${plan.value}`
}
</script>

<template>
  <form class="max-w-sm space-y-4" @submit.prevent="submit">
    <UiRadioGroup v-model="plan" class="flex flex-col gap-2">
      <label class="flex cursor-pointer items-center gap-2 text-sm">
        <UiRadioItem value="free" />
        <UiLabel class="cursor-pointer font-normal">Free</UiLabel>
      </label>
      <label class="flex cursor-pointer items-center gap-2 text-sm">
        <UiRadioItem value="pro" />
        <UiLabel class="cursor-pointer font-normal">Pro</UiLabel>
      </label>
    </UiRadioGroup>
    <UiButton type="submit">Save</UiButton>
    <p v-if="message" class="m-0 text-sm text-muted-foreground">{{ message }}</p>
  </form>
</template>
