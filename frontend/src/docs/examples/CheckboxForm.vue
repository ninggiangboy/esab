<script setup lang="ts">
import { ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiCheckbox from '@/components/ui/UiCheckbox.vue'
import UiFieldGroup from '@/components/ui/UiFieldGroup.vue'

const marketing = ref(false)
const terms = ref(false)
const submitted = ref<string | null>(null)

function submit() {
  if (!terms.value) {
    submitted.value = 'You must accept the terms.'
    return
  }
  submitted.value = `OK — marketing: ${marketing.value}`
}
</script>

<template>
  <form class="max-w-sm space-y-4" @submit.prevent="submit">
    <UiFieldGroup class="flex flex-col gap-3">
      <UiCheckbox v-model:checked="terms">
        I agree to the <span class="text-primary">terms</span>
      </UiCheckbox>
      <UiCheckbox v-model:checked="marketing"> Email me product updates </UiCheckbox>
    </UiFieldGroup>
    <UiButton type="submit">Submit</UiButton>
    <p v-if="submitted" class="m-0 text-sm text-muted-foreground">{{ submitted }}</p>
  </form>
</template>
