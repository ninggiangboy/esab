<script setup lang="ts">
import { reactive, ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'

const form = reactive({ qty: '2' })
const message = ref('')

function submit() {
  message.value = `Qty saved: ${form.qty}`
}
</script>

<template>
  <form class="max-w-xs space-y-3" @submit.prevent="submit">
    <div class="grid gap-1.5">
      <UiLabel for="nf-fqty">Items</UiLabel>
      <UiInput
        id="nf-fqty"
        type="number"
        min="1"
        :value="form.qty"
        @input="form.qty = ($event.target as HTMLInputElement).value"
      />
    </div>
    <UiButton type="submit">Submit</UiButton>
    <p v-if="message" class="m-0 text-xs text-muted-foreground">{{ message }}</p>
  </form>
</template>
