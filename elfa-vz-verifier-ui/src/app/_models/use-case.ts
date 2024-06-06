import {AttributeGroup} from '@app/_models/attribute-group';

export class UseCase {
  id: string;
  title: string;
  description: string;
  order: number;
  attributeGroups: AttributeGroup[];

  constructor(id: string, title: string, description: string, order: number, attributeGroups: AttributeGroup[]) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.order = order;
    this.attributeGroups = attributeGroups;
  }
}
