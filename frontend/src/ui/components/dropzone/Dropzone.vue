<script setup lang="ts">
import { computed, ref } from 'vue'
import { cn } from '@/ui/lib/utils'

const props = defineProps<{
  class?: string
}>()

const emit = defineEmits<{
  dropFiles: [files: FileList | null]
}>()

const isOver = ref(false)
const rootRef = ref<HTMLDivElement | null>(null)

const rootClass = computed(() =>
  cn(
    'flex h-[150px] w-[300px] flex-col items-center justify-center gap-2 rounded-md border border-dashed text-sm ring-offset-background',
    'data-[drop-target]:border-solid data-[drop-target]:border-primary data-[drop-target]:bg-accent',
    'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2',
    isOver.value && 'border-solid border-primary bg-accent',
    props.class,
  ),
)

function onDragOver(e: DragEvent) {
  e.preventDefault()
  isOver.value = true
}
function onDragLeave() {
  isOver.value = false
}
function onDrop(e: DragEvent) {
  e.preventDefault()
  isOver.value = false
  emit('dropFiles', e.dataTransfer?.files ?? null)
}
</script>

<template>
  <div
    ref="rootRef"
    role="presentation"
    tabindex="0"
    :class="rootClass"
    :data-drop-target="isOver ? '' : undefined"
    @dragover="onDragOver"
    @dragleave="onDragLeave"
    @drop="onDrop"
  >
    <slot />
  </div>
</template>
