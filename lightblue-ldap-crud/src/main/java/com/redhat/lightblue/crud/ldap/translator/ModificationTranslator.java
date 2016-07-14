package com.redhat.lightblue.crud.ldap.translator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.redhat.lightblue.common.ldap.LdapFieldNameTranslator;
import com.redhat.lightblue.eval.UpdateIterator;
import com.redhat.lightblue.metadata.EntityMetadata;
import com.redhat.lightblue.metadata.FieldTreeNode;
import com.redhat.lightblue.query.ArrayAddExpression;
import com.redhat.lightblue.query.FieldAndRValue;
import com.redhat.lightblue.query.ForEachExpression;
import com.redhat.lightblue.query.PartialUpdateExpression;
import com.redhat.lightblue.query.RValueExpression;
import com.redhat.lightblue.query.SetExpression;
import com.redhat.lightblue.query.UnsetExpression;
import com.redhat.lightblue.query.UpdateExpression;
import com.redhat.lightblue.query.UpdateExpressionList;
import com.redhat.lightblue.util.Path;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;

public class ModificationTranslator {

    private final LdapFieldNameTranslator fieldNameTranslator;

    public ModificationTranslator(LdapFieldNameTranslator fieldNameTranslator) {
        this.fieldNameTranslator = fieldNameTranslator;
    }

    public List<Modification> translate(UpdateExpression update, EntityMetadata md) {
        List<Modification> modifications = new ArrayList<>();
        new ModificationBuildingQueryIterator(modifications).iterate(update, md.getFieldTreeRoot());
        
        return modifications;
    }

    private class ModificationBuildingQueryIterator extends UpdateIterator<List<Modification>> {

        public ModificationBuildingQueryIterator(List<Modification> modifications) {
            super(modifications);
        }

        @Override
        protected boolean itrSetExpression(SetExpression expression, FieldTreeNode context, Path path) {
            for(FieldAndRValue pair : expression.getFields()){
                String attributeName = fieldNameTranslator.translateFieldName(pair.getField());
                RValueExpression rvalue = pair.getRValue();

                Object value = rvalue.getValue().getValue();
                if(value instanceof JsonNode){

                }
                else if(value instanceof Collection){

                }
                else if(value.getClass().isArray()){

                }

                switch(rvalue.getType()){
                    case _null:
                        thingToUpdate.add(new Modification(ModificationType.DELETE, attributeName));
                        break;
                    case _value:
                        switch (expression.getOp()) {
                            case _add:
                                thingToUpdate.add(new Modification(
                                        ModificationType.INCREMENT,
                                        attributeName,
                                        rvalue.getValue().getValue().toString()));
                                break;
                            case _set:
                                thingToUpdate.add(new Modification(
                                        ModificationType.REPLACE,
                                        attributeName,
                                        )));
                                break;
                            default:
                                throw new UnsupportedOperationException("Unsupported UpdateOperator: " + expression.getOp());
                        }
                        break;
                    case _dereference:
                        //TODO
                    default:
                        throw new UnsupportedOperationException("Unsupported RValueType: " + rvalue.getType());
                }
            }
        }

        @Override
        protected boolean itrUnsetExpression(UnsetExpression expression, FieldTreeNode context, Path path) {
            boolean updated = false;

            for (Path p : expression.getFields()) {
                String attributeName = fieldNameTranslator.translateFieldName(p);
                thingToUpdate.add(new Modification(ModificationType.DELETE, attributeName));
                updated = true;
            }

            return updated;
        }

        @Override
        protected boolean itrForEachExpression(ForEachExpression expression, FieldTreeNode context, Path path) {
            return false;
        }

        @Override
        protected boolean itrArrayAddExpression(ArrayAddExpression expression, FieldTreeNode context, Path path) {
            return false;
        }

    }

}
