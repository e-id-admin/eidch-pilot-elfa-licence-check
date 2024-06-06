import {AttributeGroup} from './attribute-group';
import {Attribute} from './attribute';

describe('AttributeGroup', () => {
  describe('constructor', () => {
    it('should properly initialize the instance', () => {
      const attrs = [new Attribute('attr1', 'type1'), new Attribute('attr2', 'type2')];
      const attributeGroup = new AttributeGroup('groupName', 1, attrs);

      expect(attributeGroup.name).toEqual('groupName');
      expect(attributeGroup.order).toEqual(1);
      expect(attributeGroup.attributes).toEqual(attrs);
    });
  });

  describe('getAttribute', () => {
    it('should return the proper attribute by its name', () => {
      const attrs = [new Attribute('attr1', 'type1'), new Attribute('attr2', 'type2')];
      const attributeGroup = new AttributeGroup('groupName', 1, attrs);

      const returnedAttribute = attributeGroup.getAttribute('attr1');

      expect(returnedAttribute).toEqual(attrs[0]);
    });

    it("should return undefined when there's no attribute of a given name", () => {
      const attrs = [new Attribute('attr1a', 'type1'), new Attribute('attr2', 'type2')];
      const attributeGroup = new AttributeGroup('groupName', 1, attrs);

      const returnedAttribute = attributeGroup.getAttribute('non-existing-name');

      expect(returnedAttribute).toBeUndefined();
    });
  });
});
