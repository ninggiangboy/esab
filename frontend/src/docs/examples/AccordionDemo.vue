<script setup lang="ts">
import { ref } from 'vue'

import UiAccordion from '@/components/ui/UiAccordion.vue'
import UiAccordionContent from '@/components/ui/UiAccordionContent.vue'
import UiAccordionItem from '@/components/ui/UiAccordionItem.vue'
import UiAccordionTrigger from '@/components/ui/UiAccordionTrigger.vue'

const open = ref('item-1')
</script>

<template>
  <UiAccordion v-model="open" type="single" collapsible class="w-full max-w-md">
    <UiAccordionItem value="item-1">
      <UiAccordionTrigger>Is it accessible?</UiAccordionTrigger>
      <UiAccordionContent> Yes. It uses Radix Vue primitives under the hood. </UiAccordionContent>
    </UiAccordionItem>
    <UiAccordionItem value="item-2">
      <UiAccordionTrigger>Is it styled?</UiAccordionTrigger>
      <UiAccordionContent>
        Yes. Matches your Tailwind theme and ships with sensible defaults.
      </UiAccordionContent>
    </UiAccordionItem>
    <UiAccordionItem value="item-3">
      <UiAccordionTrigger>Is it animated?</UiAccordionTrigger>
      <UiAccordionContent>
        Content height transitions are handled by the accordion content region.
      </UiAccordionContent>
    </UiAccordionItem>
  </UiAccordion>
</template>
