<script setup lang="ts">
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { docFormToast } from '@/docs/examples/_internal/docFormSubmit'
import { Button } from '@/ui/components/button'
import {
  Form,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  FormVm,
  useForm,
} from '@/ui/components/form'
import { BsSelect, type BsSelectOption } from '@/ui/components/select'

const languageOptions: BsSelectOption[] = [
  { id: 'en', name: 'English' },
  { id: 'es', name: 'Spanish' },
  { id: 'fr', name: 'French' },
  { id: 'de', name: 'German' },
  { id: 'it', name: 'Italian' },
]

const roleOptions: BsSelectOption[] = [
  { id: 'admin', name: 'Admin' },
  { id: 'user', name: 'User' },
]

const schema = toTypedSchema(
  z.object({
    role: z.string().min(1),
    languages: z.array(z.string()).optional(),
  }),
)

const { handleSubmit } = useForm({
  validationSchema: schema,
  initialValues: {
    role: 'admin',
    languages: [] as string[],
  },
})

const onSubmit = handleSubmit((values) => {
  docFormToast(values)
})
</script>

<template>
  <Form class="w-full space-y-3" @submit="onSubmit">
    <FormField v-slot="{ componentField, errors }" name="role">
      <FormItem>
        <FormLabel>Role</FormLabel>
        <FormVm generic="string | undefined" v-slot="vm" :component-field="componentField">
          <BsSelect
            v-bind="vm"
            clearable
            :options="roleOptions"
            :class="errors.length ? 'ring-2 ring-destructive' : ''"
          />
        </FormVm>
        <FormMessage />
      </FormItem>
    </FormField>
    <FormField v-slot="{ componentField }" name="languages">
      <FormItem>
        <FormLabel>Language</FormLabel>
        <FormVm generic="string[]" v-slot="vm" :component-field="componentField">
          <BsSelect v-bind="vm" multiple :options="languageOptions" />
        </FormVm>
        <FormMessage />
      </FormItem>
    </FormField>
    <div class="grid grid-cols-2 gap-2 py-2">
      <Button type="button" variant="outline"> Cancel </Button>
      <Button type="submit"> Save </Button>
    </div>
  </Form>
</template>
