<script setup lang="ts">
import { reactive, ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
import UiSelect from '@/components/ui/UiSelect.vue'

const form = reactive({ plan: 'pro' })
const message = ref('')

function submit() {
  message.value = `Plan: ${form.plan}`
}
</script>

<template>
  <form class="max-w-sm space-y-3" @submit.prevent="submit">
    <div class="grid gap-1.5">
      <UiLabel for="sf-plan">Plan</UiLabel>
      <UiSelect
        id="sf-plan"
        v-model="form.plan"
        name="plan"
        :options="[
          { value: 'free', label: 'Free' },
          { value: 'pro', label: 'Pro' },
          { value: 'enterprise', label: 'Enterprise' },
        ]"
      />
    </div>
    <UiButton type="submit">Save</UiButton>
    <p v-if="message" class="m-0 text-xs text-muted-foreground">{{ message }}</p>
  </form>
</template>
