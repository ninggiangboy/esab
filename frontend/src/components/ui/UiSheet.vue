<script setup lang="ts">
import { useAttrs } from 'vue'
import {
  DialogContent,
  DialogDescription,
  DialogOverlay,
  DialogPortal,
  DialogRoot,
  DialogTitle,
  DialogTrigger,
} from 'radix-vue'

import { cn } from '@/lib/utils'

defineOptions({ inheritAttrs: false })

const open = defineModel<boolean>({ default: false })

const attrs = useAttrs()

const overlayClass = cn(
  'fixed inset-0 z-50 bg-black/70',
  'data-[state=closed]:animate-out data-[state=closed]:fade-out-0',
  'data-[state=open]:animate-in data-[state=open]:fade-in-0',
)

const contentClass = cn(
  'fixed right-0 top-0 z-50 flex h-full w-full max-w-md flex-col gap-4 border-l border-border bg-background p-6 shadow-2xl outline-none',
  'data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=open]:slide-in-from-right data-[state=closed]:slide-out-to-right',
)
</script>

<template>
  <DialogRoot v-model:open="open" v-bind="{ ...attrs, class: undefined }">
    <DialogTrigger as-child>
      <slot name="trigger" />
    </DialogTrigger>
    <DialogPortal>
      <DialogOverlay :class="overlayClass" />
      <DialogContent :class="cn(contentClass, attrs.class as string | undefined)">
        <div v-if="$slots.title || $slots.description">
          <DialogTitle v-if="$slots.title" class="text-lg font-semibold">
            <slot name="title" />
          </DialogTitle>
          <DialogDescription v-if="$slots.description" class="text-sm text-muted-foreground">
            <slot name="description" />
          </DialogDescription>
        </div>
        <slot />
        <div v-if="$slots.footer" class="mt-auto flex justify-end gap-2 border-t border-border pt-4">
          <slot name="footer" />
        </div>
      </DialogContent>
    </DialogPortal>
  </DialogRoot>
</template>
