<script setup lang="ts">
import { computed, onMounted, ref, shallowRef, watch } from 'vue'
import { getPreviewComponent, loadPreviewSource } from '@/docs/examples/previewRegistry'
import { getShikiHighlighter } from '@/docs/mdx/shikiHighlighter'
import { cn } from '@/ui/lib/utils'

const props = withDefaults(
  defineProps<{
    name: string
    class?: string
    withPreview?: boolean
  }>(),
  {
    withPreview: true,
  },
)

const Preview = shallowRef<ReturnType<typeof getPreviewComponent>>(null)
const html = ref('')
const copyDone = ref(false)

const rootClass = computed(() =>
  cn(
    'w-full mt-4',
  ),
)

async function highlightSource(code: string) {
  const highlighter = await getShikiHighlighter()
  return highlighter.codeToHtml(code, {
    lang: 'vue',
    themes: {
      light: 'github-light',
      dark: 'github-dark',
    },
  })
}

async function refresh() {
  copyDone.value = false
  const comp = getPreviewComponent(props.name)
  Preview.value = comp
  const source = await loadPreviewSource(props.name)
  html.value = source ? await highlightSource(source) : '<pre>Source not found</pre>'
}

onMounted(refresh)
watch(() => props.name, refresh)

async function copy() {
  const source = await loadPreviewSource(props.name)
  if (!source) return
  await navigator.clipboard.writeText(source)
  copyDone.value = true
  setTimeout(() => {
    copyDone.value = false
  }, 2000)
}
</script>

<template>
  <div :class="rootClass">
    <div class="p-1.5 space-y-1.5 border border-border rounded-xl bg-background">
      <div
        v-if="withPreview"
        :class="
          cn(
            'p-5 min-h-25 docs-not-prose flex items-center justify-center',
            props.class,
          )
        "
      >
        <component :is="Preview" v-if="Preview" />
        <span v-else class="text-sm text-destructive">Unknown preview: {{ name }}</span>
      </div>

      <div class="relative border border-border rounded-md overflow-hidden">
        <button
          type="button"
          class="absolute right-2 top-2 z-1 text-xs px-2 py-1 rounded-md bg-background-secondary border border-border hover:bg-background-tertiary text-foreground"
          @click="copy"
        >
          {{ copyDone ? 'Copied' : 'Copy' }}
        </button>
        <div class="overflow-x-auto max-h-120 overflow-y-auto text-[13px]">
          <!-- eslint-disable-next-line vue/no-v-html -->
          <div class="shiki-wrapper p-3" v-html="html" />
        </div>
      </div>
    </div>
  </div>
</template>
