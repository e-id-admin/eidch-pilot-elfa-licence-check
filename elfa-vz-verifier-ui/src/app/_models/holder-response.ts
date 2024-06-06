export class HolderResponse {
  attributes: Record<string, string>;

  constructor(attributes: Record<string, string>) {
    this.attributes = attributes;
  }
}
