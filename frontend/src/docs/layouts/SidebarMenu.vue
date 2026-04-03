<script setup lang="ts">
import { cn } from '@/ui/lib/utils'
import { computed } from 'vue'
import { RouterLink, useRoute } from 'vue-router'

type Item = { title: string; href: string }
type Group = { title: string; items: Item[] }

const route = useRoute()

const guideGroups: Group[] = [
  {
    title: 'Getting Started',
    items: [{ title: 'Introduction', href: '/docs/guide/introduction' }],
  },
]

function item(title: string, slug: string): Item {
  return { title, href: `/docs/ui/${slug}` }
}

const uiGroups: Group[] = [
  {
    title: 'Actions & inputs',
    items: [
      item('Button', 'button'),
      item('Field', 'field'),
      item('Input', 'input'),
      item('Textarea', 'textarea'),
      item('Password', 'password'),
      item('Search field', 'searchfield'),
      item('Number field', 'numberfield'),
      item('Date field', 'datefield'),
      item('Checkbox', 'checkbox'),
      item('Radio group', 'radio-group'),
      item('Switch', 'switch'),
      item('Slider', 'slider'),
      item('Toggle', 'toggle'),
      item('Textfield (module)', 'textfield'),
    ],
  },
  {
    title: 'Layout & display',
    items: [
      item('Separator', 'separator'),
      item('Card', 'card'),
      item('Badge', 'badge'),
      item('Avatar', 'avatar'),
      item('Skeleton', 'skeleton'),
      item('Table', 'table'),
      item('Breadcrumbs', 'breadcrumbs'),
      item('Scroll area', 'scroll-area'),
      item('Spinner', 'spinner'),
      item('Loading overlay', 'loading-overlay'),
      item('App sidebar', 'sidebar'),
    ],
  },
  {
    title: 'Overlay & navigation',
    items: [
      item('Accordion', 'accordion'),
      item('Tabs', 'tabs'),
      item('Popover', 'popover'),
      item('Tooltip', 'tooltip'),
      item('Dialog', 'dialog'),
      item('Sheet', 'sheet'),
      item('Collapsible', 'collapsible'),
      item('Dropdown menu', 'menu'),
      item('Listbox', 'listbox'),
      item('Pagination', 'pagination'),
    ],
  },
  {
    title: 'Data & pickers',
    items: [
      item('Calendar', 'calendar'),
      item('Date picker', 'datepicker'),
      item('Select', 'select'),
      item('Data table', 'datatable'),
      item('Grid list', 'grid-list'),
    ],
  },
  {
    title: 'Feedback & upload',
    items: [
      item('Progress', 'progress'),
      item('Sonner (toast)', 'sonner'),
      item('Confirm dialog', 'confirm-dialog'),
      item('Dropzone', 'dropzone'),
      item('Uploader trigger', 'uploader-trigger'),
      item('Uploader', 'uploader'),
    ],
  },
  {
    title: 'Forms & app',
    items: [
      item('Form (vee-validate)', 'form'),
      item('Provider', 'provider'),
    ],
  },
]

const groups = computed(() => (route.path.includes('/docs/ui') ? uiGroups : guideGroups))
</script>

<template>
  <div class="docs-sidebar-scroll px-3 pb-8 pt-2 overflow-y-auto max-h-[calc(100vh-8rem)]">
    <div v-for="group in groups" :key="group.title" class="mb-6">
      <div class="px-3 mb-2 text-xs font-medium text-muted-foreground tracking-wide uppercase">
        {{ group.title }}
      </div>
      <ul class="list-none space-y-0.5">
        <li v-for="item in group.items" :key="item.href">
          <RouterLink
            :to="item.href"
            :class="
              cn(
                'block rounded-md px-3 py-1.5 text-[13px] text-secondary-foreground no-underline hover:bg-sidebar-accent',
                route.path === item.href && 'bg-sidebar-accent font-medium text-sidebar-accent-foreground',
              )
            "
          >
            {{ item.title }}
          </RouterLink>
        </li>
      </ul>
    </div>
  </div>
</template>
