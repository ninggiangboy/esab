<script setup lang="ts">
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { docFormToast } from '@/docs/examples/_internal/docFormSubmit'
import { Button } from '@/ui/components/button'
import { Checkbox, CheckboxGroup } from '@/ui/components/checkbox'
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  FormVm,
  useForm,
} from '@/ui/components/form'
import { TextArea } from '@/ui/components/textfield'

const schema = toTypedSchema(
  z.object({
    interest: z.array(z.string()),
    bio: z.string(),
    acceptTerm: z.boolean().refine((v) => v, {
      message: 'Please accept the terms and conditions',
    }),
  }),
)

const { handleSubmit } = useForm({
  validationSchema: schema,
  initialValues: {
    interest: [] as string[],
    bio: '',
    acceptTerm: false,
  },
})

const onSubmit = handleSubmit((v) => docFormToast(v))
</script>

<template>
  <Form class="space-y-5" @submit="onSubmit">
    <!-- No `type="checkbox"`: group v-model is the whole string[]; checkbox type would nest arrays. -->
    <FormField v-slot="{ componentField }" name="interest">
      <FormItem>
        <FormLabel>Select your interests</FormLabel>
        <FormDescription>Pick one or more.</FormDescription>
        <FormVm generic="string[]" v-slot="vm" :component-field="componentField">
          <CheckboxGroup v-bind="vm" class="grid grid-cols-3 gap-4">
            <Checkbox value="reading">
              Reading
            </Checkbox>
            <Checkbox value="writing">
              Writing
            </Checkbox>
            <Checkbox value="coding">
              Coding
            </Checkbox>
          </CheckboxGroup>
        </FormVm>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="bio">
      <FormItem>
        <FormLabel>Bio</FormLabel>
        <FormControl v-slot="controlProps">
          <TextArea
            v-bind="{ ...componentField, ...controlProps }"
            placeholder="Type your bio here..."
            class="min-h-24"
          />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>

    <FormField v-slot="{ componentField }" name="acceptTerm" type="checkbox">
      <FormItem>
        <FormVm generic="boolean | 'indeterminate'" v-slot="vm" :component-field="componentField">
          <Checkbox v-bind="vm">
            I accept the terms and conditions
          </Checkbox>
        </FormVm>
        <FormMessage />
      </FormItem>
    </FormField>

    <Button type="submit">
      Submit
    </Button>
  </Form>
</template>
